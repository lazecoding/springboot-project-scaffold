package lazecoding.project.common.util.audio;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * 音频格式转换器
 *
 * @author lazecoding
 */
public class AudioFormatConverter {

    /**
     * pcm 二进制转 wav 二进制
     */
    public static byte[] pcmToWav(byte[] pcmBytes) {
        return addWavHeader(pcmBytes, buildWavHeader(pcmBytes.length));
    }

    /**
     * wav 二进制转 pcm 二进制
     *
     * @param wavBytes wav 二进制
     */
    public static byte[] wavToPcm(byte[] wavBytes) {
        return removeWavHeader(changeFormatToWav(wavBytes));
    }

    /**
     * 为 pcm 二进制增加 wav 文件头
     *
     * @param pcmBytes    pcm 二进制
     * @param headerBytes wav 文件头二进制
     */
    private static byte[] addWavHeader(byte[] pcmBytes, byte[] headerBytes) {
        byte[] result = new byte[44 + pcmBytes.length];
        System.arraycopy(headerBytes, 0, result, 0, 44);
        System.arraycopy(pcmBytes, 0, result, 44, pcmBytes.length);
        return result;
    }

    /**
     * 处理 wav 二进制
     *
     * @param wavBytes 原 wav 二进制
     */
    private static byte[] changeFormatToWav(byte[] wavBytes) {
        AudioFormat format = new AudioFormat(
                AudioFormats.RATE,
                AudioFormats.BIT_DEPTH,
                AudioFormats.CHANNELS,
                true,
                false
        );
        try (final AudioInputStream originalAudioStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(wavBytes));
             final AudioInputStream formattedAudioStream = AudioSystem.getAudioInputStream(format, originalAudioStream);
             final AudioInputStream lengthAddedAudioStream = new AudioInputStream(formattedAudioStream, format, wavBytes.length);
             final ByteArrayOutputStream convertedOutputStream = new ByteArrayOutputStream()) {
            AudioSystem.write(lengthAddedAudioStream, AudioFileFormat.Type.WAVE, convertedOutputStream);
            return convertedOutputStream.toByteArray();
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 移除 WAV 二进制文件头（获取 pcm 二进制）
     *
     * @param wavBytes wav 二进制
     */
    private static byte[] removeWavHeader(byte[] wavBytes) {
        return Arrays.copyOfRange(wavBytes, 44, wavBytes.length);
    }

    /**
     * 构建 wav 文件头
     *
     * @param dataLength data 长度
     */
    private static byte[] buildWavHeader(int dataLength) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            writeChar(bos, new char[]{'R', 'I', 'F', 'F'});
            writeInt(bos, dataLength + (44 - 8));
            writeChar(bos, new char[]{'W', 'A', 'V', 'E'});
            writeChar(bos, new char[]{'f', 'm', 't', ' '});
            writeInt(bos, 16);
            writeShort(bos, 0x0001);
            writeShort(bos, AudioFormats.CHANNELS);
            writeInt(bos, AudioFormats.RATE);
            writeInt(bos, (short) (AudioFormats.CHANNELS * 2) * AudioFormats.RATE);
            writeShort(bos, (short) (AudioFormats.CHANNELS * 2));
            writeShort(bos, 16);
            writeChar(bos, new char[]{'d', 'a', 't', 'a'});
            writeInt(bos, dataLength);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeShort(ByteArrayOutputStream bos, int s) throws IOException {
        byte[] arr = new byte[2];
        arr[1] = (byte) ((s << 16) >> 24);
        arr[0] = (byte) ((s << 24) >> 24);
        bos.write(arr);
    }

    private static void writeInt(ByteArrayOutputStream bos, int n) throws IOException {
        byte[] buf = new byte[4];
        buf[3] = (byte) (n >> 24);
        buf[2] = (byte) ((n << 8) >> 24);
        buf[1] = (byte) ((n << 16) >> 24);
        buf[0] = (byte) ((n << 24) >> 24);
        bos.write(buf);
    }

    private static void writeChar(ByteArrayOutputStream bos, char[] id) {
        for (char c : id) {
            bos.write(c);
        }
    }
}
