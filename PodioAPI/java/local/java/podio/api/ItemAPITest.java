package local.java.podio.api;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.joda.time.LocalDate;
import org.junit.Test;

import com.podio.APIFactory;
import com.podio.ResourceFactory;
import com.podio.app.ApplicationFieldType;
import com.podio.common.AuthorizationEntityType;
import com.podio.common.Reference;
import com.podio.common.ReferenceType;
import com.podio.filter.CreatedByFilterBy;
import com.podio.filter.CreatedOnFilterBy;
import com.podio.filter.CreatedViaFilterBy;
import com.podio.filter.FilterByValue;
import com.podio.filter.PodioDateInterval;
import com.podio.item.*;
import com.podio.oauth.OAuthClientCredentials;
import com.podio.oauth.OAuthUsernameCredentials;
import com.podio.rating.RatingType;
import com.podio.rating.RatingValue;

public class ItemAPITest {

	private static final String USERNAME = "alex.white@oval.uk.com";
	
	private static final String PASSWORD = "ba5ildonbond";
	
	private static final String APP_NAME = "app-b-search-populator";
	
	private static final String API_KEY = "maSP05ESQoJQuMdhFhkYsAATYenEGJ2nEIU2zvpYpxJU66G8vDyiC5wfImMxHhm8";

	private ItemAPI getAPI() {
		
		ResourceFactory resourceFactory = new ResourceFactory(
		        new OAuthClientCredentials(APP_NAME, API_KEY),
		        new OAuthUsernameCredentials(USERNAME, PASSWORD));
		APIFactory apiFactory = new APIFactory(resourceFactory);
		
		ItemAPI item = apiFactory.getAPI(ItemAPI.class);
		
		return item;
	}

	@Test
	public void getItem() {
		Item item = getAPI().getItem(1);

		Assert.assertEquals(item.getId(), 1);
		Assert.assertEquals(item.getExternalId(), "12");
		Assert.assertEquals(item.getApplication().getId(), 1);
		Assert.assertEquals(item.getApplication().getConfiguration().getName(), "Bugs");
		Assert.assertEquals(item.getApplication().getConfiguration().getItemName(), "Bug");
		Assert.assertEquals(item.getApplication().getConfiguration().getIcon(), "23.png");
		Assert.assertTrue(item.getFields().size() >= 19);
		FieldValuesView field = item.getFields().get(0);
		Assert.assertEquals(field.getId(), 1);
		Assert.assertEquals(field.getExternalId(), "is-hired");
		Assert.assertEquals(field.getType(), ApplicationFieldType.CATEGORY);
		Assert.assertEquals(field.getLabel(), "Is hired?");
		Assert.assertEquals(field.getValues().size(), 2);
		Assert.assertEquals(((Map<String, Object>) field.getValues().get(0).get("value")).get("text"), "no");
		Assert.assertEquals(field.getValues().get(0).size(), 1);
		Assert.assertEquals(((Map<String, Object>)field.getValues().get(1).get("value")).get("text"), "yes");
		Assert.assertEquals(field.getValues().get(1).size(), 1);
		Assert.assertEquals(item.getComments().size(), 2);
		Assert.assertEquals(item.getRevisions().size(), 1);
		Assert.assertEquals(item.getRatings().get(RatingType.APPROVED)
				.getCounts(1).getTotal(), 1);
		Assert.assertEquals(item.getRatings().get(RatingType.APPROVED)
				.getCounts(1).getUsers().get(0).getUserId().intValue(), 2);
		Assert.assertEquals(item.getFiles().size(), 1);
		Assert.assertEquals(item.getFiles().get(0).getId(), 1);
		Assert.assertEquals(item.getTags().size(), 2);
		Assert.assertEquals(item.getTags().get(0), "release");
		Assert.assertEquals(item.getTags().get(1), "rollout");
		Assert.assertEquals(item.isSubscribed(), true);
		Assert.assertEquals(item.getUserRatings().size(), 5);
		Assert.assertEquals(item.getUserRating(RatingType.APPROVED),
				new Integer(RatingValue.APPROVED_APPROVES));
		Assert.assertEquals(item.getUserRating(RatingType.FIVESTAR),
				new Integer(RatingValue.FIVESTAR_1_STAR));
		Assert.assertEquals(item.getUserRating(RatingType.YESNO), new Integer(
				RatingValue.YESNO_YES));
		Assert.assertEquals(item.getUserRating(RatingType.RSVP), new Integer(
				RatingValue.RSVP_ATTEND));
	}

	@Test
	public void getItemValues() {
		List<FieldValuesView> values = getAPI().getItemValues(1);

		Assert.assertTrue(values.size() >= 19);
		Assert.assertEquals(values.get(4).getValues().size(), 1);
		Assert.assertEquals(values.get(4).getValues().get(0).size(), 1);
		Assert.assertEquals(((Map<String, Object>) values.get(4).getValues()
				.get(0).get("value")).get("item_id"), 2);
		Assert.assertEquals(((Map<String, Object>) values.get(4).getValues()
				.get(0).get("value")).get("title"), "no");
	}

	@Test
	public void getItemFieldValues() {
		List<Map<String, Object>> values = getAPI().getItemFieldValues(1, 5);

		Assert.assertEquals(values.size(), 1);
		Assert.assertEquals(values.get(0).size(), 1);
		Assert.assertEquals(((Map<String, Object>) values.get(0).get("value"))
				.get("item_id"), 2);
		Assert.assertEquals(
				((Map<String, Object>) values.get(0).get("value")).get("title"),
				"no");
	}

	@Test
	public void getItemReferences() {
		List<ItemReference> references = getAPI().getItemReference(2);

		Assert.assertEquals(references.size(), 1);
		ItemReference reference = references.get(0);
		Assert.assertEquals(reference.getApplication().getId(), 1);
		Assert.assertEquals(reference.getItems().size(), 1);
		ItemMicro item = reference.getItems().get(0);
		Assert.assertEquals(item.getId(), 1);
		Assert.assertEquals(item.getTitle(), "no & yes");
	}

	@Test
	public void getItemRevision() {
		ItemRevision revision = getAPI().getItemRevision(1, 0);

		Assert.assertEquals(revision.getCreatedBy().getType(),
				AuthorizationEntityType.USER);
		Assert.assertEquals(revision.getCreatedBy().getId(), 1);
	}

	@Test
	public void getItemRevisionDifference() {
		List<ItemFieldDifference> differences = getAPI()
				.getItemRevisionDifference(2, 0, 1);

		Assert.assertEquals(differences.size(), 2);
		Assert.assertEquals(differences.get(0).getId(), 1);
		Assert.assertEquals(differences.get(0).getType(),
				ApplicationFieldType.CATEGORY);
		Assert.assertEquals(differences.get(0).getLabel(), "Is hired?");
		Assert.assertEquals(differences.get(0).getFrom().size(), 1);
		Assert.assertEquals(((Map<String, Object>) differences.get(0).getFrom().get(0).get("value")).get("text"),
				"yes");
		Assert.assertEquals(differences.get(0).getTo().size(), 1);
		Assert.assertEquals(((Map<String, Object>) differences.get(0).getTo().get(0).get("value")).get("text"),
				"no");
	}

	@Test
	public void getItemRevisions() {
		List<ItemRevision> revisions = getAPI().getItemRevisions(2);

		Assert.assertEquals(revisions.size(), 2);
	}

	@Test
	public void getItems() {
		ItemsResponse response = getAPI().getItems(1, null, null, null, null);

		Assert.assertEquals(response.getTotal(), 2);
		Assert.assertEquals(response.getFiltered(), 2);
		Assert.assertEquals(response.getItems().size(), 2);
	}

	@Test
	public void getItemsByExternalId() {
		ItemsResponse response = getAPI().getItemsByExternalId(1, "12");

		Assert.assertEquals(response.getItems().size(), 1);
		Assert.assertEquals(response.getItems().get(0).getId(), 1);
	}

	@Test
	public void getItemsFilterByCreatedBy() {
		ItemsResponse response = getAPI().getItems(
				1,
				null,
				null,
				null,
				null,
				new FilterByValue<List<Reference>>(new CreatedByFilterBy(),
						Arrays.asList(new Reference(ReferenceType.USER, 0))));

		Assert.assertEquals(response.getItems().size(), 1);
		Assert.assertEquals(response.getItems().get(0).getId(), 1);
	}

	@Test
	public void getItemsFilterByCreatedVia() {
		ItemsResponse response = getAPI().getItems(
				1,
				null,
				null,
				null,
				null,
				new FilterByValue<List<Integer>>(new CreatedViaFilterBy(),
						Arrays.asList(2)));

		Assert.assertEquals(response.getItems().size(), 1);
		Assert.assertEquals(response.getItems().get(0).getId(), 2);
	}

	@Test
	public void getItemsFilterByCreatedOn() {
		ItemsResponse response = getAPI().getItems(
				1,
				null,
				null,
				null,
				null,
				new FilterByValue<PodioDateInterval>(new CreatedOnFilterBy(),
						PodioDateInterval.absolute(new LocalDate(2010, 8, 2), new LocalDate(2010, 8, 5))));

		Assert.assertEquals(response.getItems().size(), 2);
		Assert.assertEquals(response.getItems().get(0).getId(), 2);
		Assert.assertEquals(response.getItems().get(1).getId(), 1);
	}
}