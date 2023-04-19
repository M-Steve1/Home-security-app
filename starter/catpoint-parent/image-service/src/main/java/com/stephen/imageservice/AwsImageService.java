package com.stephen.imageservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.DetectLabelsRequest;
import software.amazon.awssdk.services.rekognition.model.DetectLabelsResponse;
import software.amazon.awssdk.services.rekognition.model.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.stream.Collectors;

public class AwsImageService implements ImageService{

    private Logger log = LoggerFactory.getLogger(AwsImageService.class);
    private static RekognitionClient rekognitionClient;

    public  AwsImageService() {
        Properties props = new Properties();

        try(InputStream is = AwsImageService.class.getClassLoader().getResourceAsStream("config.properties")) {
            props.load(is);
        } catch (IOException ioe) {
            log.error("Unable to connect to AWS rekognition", ioe);
        }

        String awsId = props.getProperty("awsId");
        String awsSecret = props.getProperty("aws.secret");
        String awsRegion = props.getProperty("aws.region");

        AwsCredentials awsCredentials = AwsBasicCredentials.create(awsId, awsSecret);
        rekognitionClient = RekognitionClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .region(Region.of(awsRegion))
                .build();
    }

    @Override
    public boolean imageContainsCat(BufferedImage image, float confidenceThreshold) {
        Image awsImage;
        try(ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ImageIO.write(image, "jpg", os);
            awsImage = Image.builder().bytes(SdkBytes.fromByteArray(os.toByteArray())).build();
        } catch (IOException ioe) {
            log.error("Error building image byte array", ioe);
            return false;
        }

        DetectLabelsRequest detectLabelsRequest = DetectLabelsRequest.builder().image(awsImage).minConfidence(confidenceThreshold).build();
        DetectLabelsResponse response = rekognitionClient.detectLabels(detectLabelsRequest);
        logLabelsForFun(response);
        return response.labels().stream().filter(l -> l.name().toLowerCase().contains("cat")).findFirst().isPresent();
    }

    private void logLabelsForFun(DetectLabelsResponse response) {
        log.info(response.labels().stream().map(label -> String.format("%s (%.1f%%)", label.name(), label.confidence())).collect(Collectors.joining(", ")));
    }
}
