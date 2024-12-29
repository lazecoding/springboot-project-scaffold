package lazecoding.project.common.util.audio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.FileInputStream;

/**
 * PCM To WAV
 *
 * @author lazecoding
 */
public class PcmToWavUtil {

    private static final Logger logger = LoggerFactory.getLogger(PcmToWavUtil.class);

    /**
     * pcmToWav
     *
     * @param pcmPath 输入的 PCM 文件路径
     * @param wavPath 输出的 WAV 文件路径
     */
    public static boolean pcmToWav(String pcmPath, String wavPath) {
        File pcmFile = new File(pcmPath);
        File wavFile = new File(wavPath);
        return pcmToWav(pcmFile, wavFile);
    }

    /**
     * pcmToWav
     *
     * @param pcmFile 输入的 PCM 文件
     * @param wavFile 输出的 WAV 文件
     */
    public static boolean pcmToWav(File pcmFile, File wavFile) {
        // 采样率
        int sampleRate = AudioFormats.RATE;
        // 通道数（立体声）
        int channels = AudioFormats.CHANNELS;
        // 位深（16 位）
        int bitDepth = AudioFormats.BIT_DEPTH;
        return pcmToWav(pcmFile, wavFile, sampleRate, channels, bitDepth);
    }

    /**
     * pcmToWav
     *
     * @param pcmFile     输入的 PCM 文件
     * @param wavFile     输出的 WAV 文件
     * @param sampleRate  采样率
     * @param channels 通道数（立体声）
     * @param bitDepth    位深（16 位）
     */
    public static boolean pcmToWav(File pcmFile, File wavFile, int sampleRate, int channels, int bitDepth) {
        boolean isSuccess = false;
        try {
            // 创建音频格式对象
            AudioFormat format = new AudioFormat(
                    sampleRate, // 采样率
                    bitDepth,   // 位深
                    channels,// 通道数
                    true,       // 是否为签名（true为有符号，false为无符号）
                    false       // 是否是大端字节序
            );
            // 创建输入流和音频输入流
            AudioInputStream pcmAudioInputStream = new AudioInputStream(new FileInputStream(pcmFile), format, pcmFile.length() / format.getFrameSize());
            // 将 PCM 输入流写入 WAV 输出流
            AudioSystem.write(pcmAudioInputStream, AudioFileFormat.Type.WAVE, wavFile);
            isSuccess = true;
            logger.debug("pcmToWav successful. pcmFile:[{}] wavFile:[{}]", pcmFile.getName(), wavFile.getName());
        } catch (Exception e) {
            logger.error("pcmToWav 异常", e);
        }
        return isSuccess;
    }
}
