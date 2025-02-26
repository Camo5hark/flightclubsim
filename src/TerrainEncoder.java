import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class TerrainEncoder {
    public static void main(String[] args) throws IOException {
        BufferedImage heightmap = ImageIO.read(new File("C:\\Users\\Andrew\\Downloads\\map.png"));
        int width = heightmap.getWidth();
        int depth = heightmap.getHeight();

        System.out.println(heightmap.getType());

        byte[] terrain = new byte[width * depth];

        for (int z = 0; z < depth; z++) {
            for (int x = 0; x < width; x++) {
                int abgr = heightmap.getRGB(x, z); // ABGR

                int b = (abgr >> 16) & 0xFF;
                int g = (abgr >> 8) & 0xFF;
                int r = abgr & 0xFF;

                terrain[z * width + x] = (byte) (((b + g + r) / 3) & 0xFF);
            }
        }

        byte[] bin = new byte[terrain.length + 8];
        byte[] widthBytes = intToBytes(width);
        byte[] depthBytes = intToBytes(depth);

        System.arraycopy(widthBytes, 0, bin, 0, widthBytes.length);
        System.arraycopy(depthBytes, 0, bin, 4, depthBytes.length);
        System.arraycopy(terrain, 0, bin, 8, terrain.length);

        GZIPOutputStream out = new GZIPOutputStream(new FileOutputStream("C:\\Users\\Andrew\\Downloads\\map.bin.gz"));
        out.write(bin);
        out.close();
    }

    private static byte[] intToBytes(int n) {
        byte[] b = new byte[Integer.BYTES];

        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) ((n >> ((b.length - i - 1) * Byte.SIZE)) & 0xFF);
        }

        return b;
    }
}
