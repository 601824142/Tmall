package com.wan.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

/**
 * @Author 万星明
 * @Date 2019/2/17
 */
public class ImageUtil {

    /**
     * 将图片获取为文件,将其返回为图片
     * @param file
     * @return
     */
    public static BufferedImage change2JPG(File file){

        try {

            //调用默认工具箱,返回从指定文件获取像素数据的图像,创建图片
            Image image = Toolkit.getDefaultToolkit().createImage(file.getAbsolutePath());
            //创建一个 PixelGrabber 对象，以从指定的图像中抓取像素矩形部分 (x, y, w, h)。
            PixelGrabber pixelGrabber = new PixelGrabber(image, 0, 0, -1, -1, true);
            //请求 Image 或 ImageProducer 开始传递像素，并等待传递完相关矩形中的所有像素。
            pixelGrabber.grabPixels();
            //获取（调整图像高度后的）像素缓冲区的高度和宽度。
            int width = pixelGrabber.getWidth(), height = pixelGrabber.getHeight();
            //定义三原色彩像素
            final int[] RGB_MASKS = { 0xFF0000, 0xFF00, 0xFF };
            //根据指定的指示 int 像素表示形式中哪些位包含红色、绿色和蓝色颜色样本的掩码构造 DirectColorModel。
            final ColorModel RGB_OPAQUE = new DirectColorModel(32, RGB_MASKS[0], RGB_MASKS[1], RGB_MASKS[2]);
            //使用指定数组构造具有单个存储单元且基于整数的 DataBuffer。
            DataBuffer buffer = new DataBufferInt((int[]) pixelGrabber.getPixels(), pixelGrabber.getWidth() * pixelGrabber.getHeight());
            //创建一个具有指定 DataBuffer、宽度、高度、扫描行间距和 band 掩码的 Raster(像素矩形数组)
            WritableRaster raster = Raster.createPackedRaster(buffer, width, height, width, RGB_MASKS, null);
            //构造一个具有指定 ColorModel 和 Raster 的新 BufferedImage。
            BufferedImage img = new BufferedImage(RGB_OPAQUE, raster, false, null);
            //返回IMG图像
            return img;

        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }

    }


    public static void resizeImage(File srcFile, int width,int height, File destFile) {

        try {
            //如果存储的文件路径的父文件夹不存在,就创建
            if(!destFile.getParentFile().exists()){
                destFile.getParentFile().mkdirs();
            }
            //读取要存储的文件,转为图片对象
            Image i = ImageIO.read(srcFile);
            i = resizeImage(i, width, height);

            ImageIO.write((RenderedImage) i, "jpg", destFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static Image resizeImage(Image srcImage, int width, int height) {
        try {

            BufferedImage buffImg = null;
            buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            buffImg.getGraphics().drawImage(srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);

            return buffImg;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


}
