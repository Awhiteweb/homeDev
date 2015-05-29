package local.database.xml;

import local.models.top.IUnitOfWork;
import local.models.top.IVideoRepo;

public class XmlUOW implements IUnitOfWork
{

	private IVideoRepo videoRepo;

	public XmlUOW()
	{
	}
	
	@Override
	public IVideoRepo VideoRepo()
	{
		if( videoRepo == null )
		{
			videoRepo = new XMLVideoRepo();
		}
		return videoRepo;
	}

}
