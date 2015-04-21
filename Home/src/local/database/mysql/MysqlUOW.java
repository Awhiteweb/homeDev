package local.database.mysql;

import local.models.top.IUnitOfWork;
import local.models.top.IVideoRepo;

public class MysqlUOW implements IUnitOfWork
{
	private IVideoRepo videoRepo;
	private MysqlConnect conn;
	
	public MysqlUOW()
	{
		this.conn = new MysqlConnect();
	}
	
	@Override
	public IVideoRepo VideoRepo()
	{
		if( videoRepo == null )
		{
			videoRepo = new MysqlVideoRepo(this.conn);
		}
		return videoRepo;
	}

}
