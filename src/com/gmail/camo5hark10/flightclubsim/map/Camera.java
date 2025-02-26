package com.gmail.camo5hark10.flightclubsim.map;

import com.gmail.camo5hark10.flightclubsim.Display;
import com.gmail.camo5hark10.flightclubsim.Main;
import com.gmail.camo5hark10.flightclubsim.MathHelper;
import org.joml.Matrix4d;
import org.joml.Quaterniond;
import org.joml.Vector3d;

public class Camera {
    public double fov;
    public Vector3d position;
    public Vector3d rotation;
    private Matrix4d projection;
    private Quaterniond orientation;
    private Matrix4d sceneView;

    public Camera(double fov, Vector3d position, Vector3d rotation) {
        this.fov = fov;
        this.position = position;
        this.rotation = rotation;

        updateProjection();
        updateSceneView();
    }

    public Camera() {
        this(Math.toRadians(90.0), new Vector3d(), new Vector3d());
    }

    public void updateProjection() {
        Display display = Main.INSTANCE.getDisplay();

        projection = new Matrix4d().perspective(fov, (double) display.width / (double) display.height, 0.001, 1000.0);
    }

    public void updateSceneView() {
        rotation.x = MathHelper.normalizeRadians(rotation.x);
        rotation.y = MathHelper.normalizeRadians(rotation.y);
        rotation.z = MathHelper.normalizeRadians(rotation.z);

        Quaterniond orientation = new Quaterniond().rotateXYZ(-rotation.x, rotation.y, rotation.z);

        this.orientation = orientation.invert(new Quaterniond());

        sceneView = new Matrix4d()
                .rotate(orientation)
                .translate(position.negate(new Vector3d()));
    }

    public Matrix4d getProjection() {
        return projection;
    }

    public Quaterniond getOrientation() {
        return orientation;
    }

    public Matrix4d getSceneView() {
        return sceneView;
    }
}
