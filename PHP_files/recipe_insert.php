<?php
require_once 'db_function.php';
$db = new DB_Functions();

//JSON response

$response = array("error"=>FALSE);

if(isset($_POST['unique_id'])&&isset($_POST['recipe_name'])&&isset($_POST['recipe_description'])){
	//recieving the post
	$unique_id = $_POST['unique_id'];
	$recipe_name = $_POST['recipe_name'];
	$recipe_description = $_POST['recipe_description'];


		//create new user
		$user = $db->insertRecipe($unique_id,$recipe_name,$recipe_description);
		if($user){
			$response["error"] = FALSE;
			$response["uid"] = $user["unique_id"];
			$response["ruid"] = $user["recipe_id"];
			$response["user"]["recipe_name"] = $user["recipe_name"];
			$response["user"]["recipe_description"] = $user["recipe_description"];
			echo json_encode($response);
		}
		else{
			$response["error"] = TRUE;
			$response["error_msg"] = "An unkown error occured in registration";
			echo json_encode($response);
		}
}

else{
	$response["error"] = TRUE;
	$response["error_msg"] = "Required parameters recipes are missing";
	echo json_encode($response);
}

?>