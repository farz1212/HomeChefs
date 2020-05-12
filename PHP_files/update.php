<?php
require_once 'db_function.php';
$db = new DB_Functions();

//JSON response

$response = array("error"=>FALSE);

if(isset($_POST['unique_id'])&&isset($_POST['username'])&&isset($_POST['email'])&&isset($_POST['phone'])){
	//recieving the post
	$unique_id = $_POST['unique_id'];
	$username = $_POST['username'];
	$email = $_POST['email'];
	$phone = $_POST['phone'];

		//update existing user
		$user = $db->updateUser($unique_id,$username,$email,$phone);
		if($user){
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
			$response["error_msg"] = "An unkown error occured in updation";
			echo json_encode($response);
		}
}

else{
	$response["error"] = TRUE;
	$response["error_msg"] = "Required parameters username, email or phone number are missing";
	echo json_encode($response);
}

?>