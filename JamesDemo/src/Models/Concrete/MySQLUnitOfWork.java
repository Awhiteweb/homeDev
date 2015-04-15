package Models.Concrete;

import Models.Abstract.IUnitOfWork;
import Models.Abstract.IVideoRepository;

public class MySQLUnitOfWork implements IUnitOfWork {

	private MySQLDbContext context;
	
	private IVideoRepository videoRepository;
	
	public MySQLUnitOfWork()
	{
		this.context = new MySQLDbContext();
	}
	
	@Override
	public IVideoRepository VideoRepository() {
		if( this.videoRepository == null )
		{
			this.videoRepository = new MySQLVideoRepository(this.context);
		}
		
		return this.videoRepository;
	}

}
