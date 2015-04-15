package Models.Concrete;

import Models.Abstract.IUnitOfWork;
import Models.Abstract.IVideoRepository;

public class TestUnitOfWork implements IUnitOfWork {

	private IVideoRepository videoRepository;
	
	public TestUnitOfWork()
	{
	}
	
	@Override
	public IVideoRepository VideoRepository() {
		if( this.videoRepository == null )
		{
			this.videoRepository = new TestVideoRepository();
		}
		
		return this.videoRepository;
	}

}
