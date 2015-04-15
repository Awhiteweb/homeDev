package local.java.podio.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import com.podio.APIFactory;
import com.podio.ResourceFactory;
import com.podio.app.AppAPI;
import com.podio.app.Application;
import com.podio.app.ApplicationField;
import com.podio.contact.Profile;
import com.podio.filter.CreatedByFilterBy;
import com.podio.filter.CreatedOnFilterBy;
import com.podio.filter.DateFieldFilterBy;
import com.podio.filter.ExternalIdFilterBy;
import com.podio.filter.FieldFilterBy;
import com.podio.filter.FieldSortBy;
import com.podio.filter.FilterBy;
import com.podio.filter.FilterByValue;
import com.podio.filter.PodioDateInterval;
import com.podio.filter.SortBy;
import com.podio.filter.TitleFilterBy;
import com.podio.item.FieldValuesView;
import com.podio.item.Item;
import com.podio.item.ItemAPI;
import com.podio.item.ItemBadge;
import com.podio.item.ItemsResponse;
import com.podio.oauth.OAuthClientCredentials;
import com.podio.oauth.OAuthUsernameCredentials;
import com.podio.user.UserAPI;

public class PodioJavaAPI
{

	private static final String USERNAME = "alex.white@oval.uk.com";
	
	private static final String PASSWORD = "ba5ildonbond";
	
	private static final String APP_NAME = "app-b-search-populator";
	
	private static final String API_KEY = "maSP05ESQoJQuMdhFhkYsAATYenEGJ2nEIU2zvpYpxJU66G8vDyiC5wfImMxHhm8";
	
	private static final int APP_ID = 11106769;
	
	public static List<ItemFromAPI> run()
	{
		
		ResourceFactory resourceFactory = new ResourceFactory(
		        new OAuthClientCredentials(APP_NAME, API_KEY),
		        new OAuthUsernameCredentials(USERNAME, PASSWORD));
		APIFactory apiFactory = new APIFactory(resourceFactory);
		
//		UserAPI userAPI = apiFactory.getAPI(UserAPI.class);

//		Profile profile = userAPI.getProfile();
//		System.out.println( profile.getName() );

//		AppAPI appAPI = apiFactory.getAPI( AppAPI.class );	

//		Application app = appAPI.getApp( APP_ID );
//		List<ApplicationField> apps = app.getFields();
//		
//		for ( ApplicationField apF : apps )
//		{
//			int i = apF.getId();
//			
//			ApplicationField a = appAPI.getField( APP_ID, i );
//			System.out.println( "field id: " + i );
//			
//		}
		
		ItemAPI itemAPI = apiFactory.getAPI( ItemAPI.class );
		
		SortBy sortBy = new FieldSortBy( 89501973 );

		new LocalDate();
		LocalDate today = LocalDate.now();
//		LocalDate past = today.minusYears( 0 );
		LocalDate future = today.plusYears( 6 );
		
		FilterByValue<PodioDateInterval> filterByDate = new FilterByValue<PodioDateInterval>(
															new DateFieldFilterBy( 89501973 ),
															PodioDateInterval.absolute(
																	today, 
																	future
																	)
															);
		
//		FilterByValue<String> filterByTitle = new FilterByValue<String>( new TitleFilterBy(), "All" );
		
		
		ItemsResponse items = itemAPI.getItems( APP_ID, null, 0, sortBy, true, filterByDate );
		
		List<ItemBadge> itemList = items.getItems();
		
//		System.out.println( "date filter " + itemList.size() );

//		ItemsResponse itemsB = itemAPI.getItems( APP_ID, null, 0, sortBy, true );
//		
//		List<ItemBadge> itemBList = itemsB.getItems();
//		
//		System.out.println( "total items " + itemBList.size() );
//		
//		System.out.println( " " );

			
		List<ItemFromAPI> list = new ArrayList<ItemFromAPI>();
		
		
		for ( ItemBadge iL : itemList )
		{

			System.out.println( iL.getTitle() );
			
			List<FieldValuesView> fields = iL.getFields();
			
			ItemFromAPI item = new ItemFromAPI();
			
			item.setTitle( iL.getTitle() );
			
			for ( FieldValuesView f : fields )
			{

				System.out.println( f.getLabel() );
//				System.out.println( f.getValues() );
				
				if ( f.getLabel().equalsIgnoreCase( "search-items" ) )
				{
					item.setData( fieldItemData( "text" , f ) );
				}
				
				if ( f.getLabel().equalsIgnoreCase( "date" ) )
				{
					item.setDate( fieldItemData( "start_date_utc" , f ) );
				}
				
								
			} // end for fields 
			list.add( item );
			System.out.println( " " );

		} // end for itemList
		
		
		return list;

	}

	private static String fieldItemData( String label,  FieldValuesView f )
	{

		String data = "";
		
		List<Map<String, ?>> map = f.getValues();
		
		for ( Map<String, ?> value : map )
		{
//						System.out.println( value.values() );
			
			if ( value.toString().contains( label ) )
			{					
				data = value.get( label ).toString();
				
			} // end if contains start_date
			
		} // end for map
		
		return data;
			
	}	
	
}
