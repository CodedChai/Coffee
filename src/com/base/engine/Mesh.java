package com.base.engine;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Mesh {

    private int vaoId;

    private int vboId;

    private int vertexCount;

    private int size;

    public Mesh(){
        vboId = glGenBuffers();
        size = 0;
    }

    public void addVertices(Vertex[] vertices){
/*
        FloatBuffer verticesBuffer = null;
        try {
            verticesBuffer = MemoryUtil.memAllocFloat(vertices.length * Vertex.SIZE);
            vertexCount = vertices.length;
            for(int i = 0; i < vertices.length; i++){
                verticesBuffer.put(vertices[i].getPos().getX());
                verticesBuffer.put(vertices[i].getPos().getY());
                verticesBuffer.put(vertices[i].getPos().getZ());
            }
            verticesBuffer.flip();

            vaoId = glGenVertexArrays();
            glBindVertexArray(vaoId);

            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
            glBindBuffer(GL_ARRAY_BUFFER, 0);

            glBindVertexArray(0);
        } finally {
            if (verticesBuffer  != null) {
                MemoryUtil.memFree(verticesBuffer);
            }
        }*/

        size = vertices.length;

        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, Util.createFlippedBuffer(vertices), GL_STATIC_DRAW);
    }

    public void draw(){
        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);

        glDrawArrays(GL_TRIANGLES, 0, size);

        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }
}
