<?php
require_once 'db_function.php';
$db = new DB_Functions();

//JSON response

$response = array("error"=>FALSE);

if(isset($_POST['username'])&&isset($_POST['password'])){
	//recieving the post
	$username = $_POST['username'];
	$password = $_POST['password'];

	$user = $db->getUserByUsernameAndPassword($username,$password);
	if($user != false){
		$response["error"] = FALSE;
		$response["uid"] = $user["unique_id"];
		$response["user"]["username"] = $user["username"];
		$response["user"]["email"] = $user["email"];
		$response["user"]["phone"] = $user["phone"];
		$response["user"]["created_at"] = $user["created_at"];
		$response["user"]["updated_at"] = $user["updated_at"];
		echo json_encode($response);
	}
	else{

	$response["error"] = TRUE;
	$response["error_msg"] = "Login information is incorrect please try again";
	echo json_encode($response);
	}
}

else{
	$response["error"] = TRUE;
	$response["error_msg"] = "Required parameters username, email, password are missing";
	echo json_encode($response);
}

?>