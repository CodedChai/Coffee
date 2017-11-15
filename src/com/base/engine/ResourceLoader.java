package com.base.engine;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;
import org.newdawn.slick.*;

import javax.imageio.ImageIO;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;


public class ResourceLoader {

    private static final int BYTES_PER_PIXEL = 4;//3 for RGB, 4 for RGBA
    public static Texture loadTexture(String fileName){

        BufferedImage image;

        try {
            image = ImageIO.read(new File("./res/textures/" + fileName));

            int[] pixels = new int[image.getWidth() * image.getHeight()];
            image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

            ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL); //4 for RGBA, 3 for RGB

            for(int y = 0; y < image.getHeight(); y++){
                for(int x = 0; x < image.getWidth(); x++){
                    int pixel = pixels[y * image.getWidth() + x];
                    buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                    buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                    buffer.put((byte) (pixel & 0xFF));               // Blue component
                    buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
                }
            }

            buffer.flip(); //FOR THE LOVE OF GOD DO NOT FORGET THIS

            // You now have a ByteBuffer filled with the color data of each pixel.
            // Now just create a texture ID and bind it. Then you can load it using
            // whatever OpenGL method you want, for example:

            int textureID = glGenTextures(); //Generate texture ID
            glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID

            //Setup wrap mode
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

            //Setup texture scaling filtering
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            //Send texel data to OpenGL
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

            //Return the texture ID so we can bind it later again
            return new Texture(textureID);

        } catch (IOException e) {
            //Error Handling Here
            e.printStackTrace();
            System.exit(1);
        }
        return null;

    }

    public static BufferedImage loadImage(String fileName)
    {
        try {
            return ImageIO.read(new File("./res/textures/" + fileName));
        } catch (IOException e) {
            //Error Handling Here
        }
        return null;
    }
/*
    public static Texture loadTexture(String fileName){
        System.out.println("./res/textures/" + fileName);
        String[] splitArray = fileName.split("\\.");
        String extension = splitArray[splitArray.length - 1];

        try{
            int id = glGenTextures();
            //int id = stb_image.getTexture(extension, new FileInputStream(new File("./res/textures/" + fileName))).getTextureID();
            return new Texture(id);
        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
*/
    public static String loadShader(String fileName){
        StringBuilder shaderSource = new StringBuilder();
        BufferedReader shaderReader = null;

        try {
            shaderReader = new BufferedReader(new FileReader("./res/shaders/"+ fileName));
            String line;
            while((line = shaderReader.readLine()) != null){
                shaderSource.append(line).append("\n");
            }

            shaderReader.close();
        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }

        return shaderSource.toString();
    }

    public static Mesh loadMesh(String fileName){
        String[] splitArray = fileName.split("\\.");
        String extension = splitArray[splitArray.length - 1];

        if(!extension.equals("obj")){
            System.err.println("Error: File format not supported for mesh data: " + extension);
            new Exception().printStackTrace();
            System.exit(1);
        }

        ArrayList<Vertex> vertices = new ArrayList<Vertex>();
        ArrayList<Integer> indices = new ArrayList<Integer>();
        BufferedReader meshReader = null;

        try {
            meshReader = new BufferedReader(new FileReader("./res/models/"+ fileName));
            String line;

            while((line = meshReader.readLine()) != null){
                String[] tokens = line.split(" ");
                tokens = Util.removeEmptyStrings(tokens);

                if(tokens.length == 0 || tokens[0].equals("#")){
                    continue;
                }
                else if(tokens[0].equals("v")){
                    vertices.add(new Vertex(new Vector3f(   Float.valueOf(tokens[1]),
                                                            Float.valueOf(tokens[2]),
                                                            Float.valueOf(tokens[3]))));
                }
                else if(tokens[0].equals("f")){
                    indices.add(Integer.parseInt(tokens[1].split("/")[0]) - 1);
                    indices.add(Integer.parseInt(tokens[2].split("/")[0]) - 1);
                    indices.add(Integer.parseInt(tokens[3].split("/")[0]) - 1);

                    // Basic triangulation for quadrilateral
                    if(tokens.length > 4){
                        indices.add(Integer.parseInt(tokens[1].split("/")[0]) - 1);
                        indices.add(Integer.parseInt(tokens[3].split("/")[0]) - 1);
                        indices.add(Integer.parseInt(tokens[4].split("/")[0]) - 1);
                    }
                }
            }
            meshReader.close();

            Mesh res = new Mesh();
            Vertex[] vertexData = new Vertex[vertices.size()];
            vertices.toArray(vertexData);

            Integer[] indexData = new Integer[indices.size()];
            indices.toArray(indexData);

            res.addVertices(vertexData, Util.toIntArray(indexData));

            return res;
        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
