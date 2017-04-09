package game;

import sage.display.*;
import sage.scene.*;
import sage.scene.shape.*;
import sage.scene.state.*;
import sage.texture.*;
import sage.renderer.*;

public class MySkyBox extends Group
{
	Quad skyBox; // Skybox consists of 6 faces

	public MySkyBox()
	{
		//skyboxQuads[Face.North.ordinal()] = new Quad(); // front face

		IRenderer renderer = DisplaySystem.getCurrentDisplaySystem().getRenderer();

		// create ZBufferState for SkyBox with depth buffer variable = false
		ZBufferState zbuff = (ZBufferState) renderer.createRenderState(RenderState.RenderStateType.ZBuffer);
		zbuff.setWritable(false);	// OpenGL equivalent: glDepthMask(false)
		zbuff.setDepthTestingEnabled(false); // OpenGL equivalent: glDisable(GL_DEPTH_TEST)
		zbuff.setFunction(ZBufferState.TestFunction.LessThanOrEqualTo);
		zbuff.setEnabled(true);

		skyBox.setRenderState(zbuff);
	}

	public void setTexture(Texture texture, int textureUnit)
	{
		TextureState texState = (TextureState) DisplaySystem.getCurrentDisplaySystem().getRenderer().createRenderState(RenderState.RenderStateType.Texture);

		// override the default environment apply mode (modulate) so nothing but skybox texels are drawn
		texture.setApplyMode(Texture.ApplyMode.Replace);

		texture.setMinificationFilter(Texture.MinificationFilter.BilinearNoMipMaps);

		texture.setMagnificationFilter(Texture.MagnificationFilter.Bilinear);

		// store texture in texture state and enable it
		texState.setTexture(texture, textureUnit);
		texState.setEnabled(true);

		skyBox.setRenderState(texState);
	}
}