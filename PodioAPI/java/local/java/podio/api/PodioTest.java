package local.java.podio.api;

import java.util.ArrayList;
import java.util.List;

public class PodioTest
{

	public static void main( String[] args )
	{
		List<ItemFromAPI> list = new ArrayList<ItemFromAPI>();
		list = PodioJavaAPI.run();

	}

}
