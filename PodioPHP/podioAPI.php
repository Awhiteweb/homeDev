<?php
echo "<pre>";

date_default_timezone_set('Europe/London');

require_once 'podio-api/PodioAPI.php';

$client_id = "app-b-search-populator";
$client_token = "maSP05ESQoJQuMdhFhkYsAATYenEGJ2nEIU2zvpYpxJU66G8vDyiC5wfImMxHhm8";
$app_id = 11106769;
$appToken = "3affea55ec6f4c7c9cd4da3d81472fc4";

Podio::setup( $client_id, $client_token );
Podio::authenticate_with_app( $app_id, $appToken );

$count = PodioItem::get_count( $app_id );

// echo $count;

$titleCol = 85559932;
$searchItem = 85560022;
$calcField = 85560023;
$dateField = 89501973;

$today = Date("Y-m-d");

$options = array(
		"filters" => array( 
				"date" => array( 
						"from" => $today 
						)
				)
		);

$result = PodioItem::filter( $app_id, $options );

foreach ( $result as $item )
{
	$title = $item -> fields[ 'title' ] -> values;
	$searchItems = $item -> fields[ 'calculation-for-search-items' ] -> values;
 	$date = $item -> fields[ 'date' ] -> values;
	
	echo $title."\n";
	echo $date['start']->format("Y-m-d")."\n\n";

	
}

