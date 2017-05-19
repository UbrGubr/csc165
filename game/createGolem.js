var JavaPackages = new JavaImporter(
	Packages.game.characters.Monster,
	
	Packages.sage.scene,
	Packages.sage.model.loader.OBJLoader,
	Packages.sage.model.loader.ogreXML.OgreXMLParser,
	
	Packages.graphicslib3D.Point3D,
	Packages.graphicslib3D.Vector3D,
	Packages.graphicslib3D.Matrix3D);

with (JavaPackages)
{
	var golem;
	function createGolems(spawn_loc)
	{
		golem = new Monster(new Point3D(-spawn_loc,2,0), new Vector3D(1,0,0), 90);
	}
}