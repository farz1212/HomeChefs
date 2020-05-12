<?php
require_once 'db_function.php';
$db = new DB_Functions();

//JSON response

$response = array("error"=>FALSE);

if(isset($_POST['username'])&&isset($_POST['gender'])&&isset($_POST['email'])&&isset($_POST['phone'])&&isset($_POST['password'])){
	//recieving the post
	$username = $_POST['username'];
	$gender = $_POST['gender'];
	$email = $_POST['email'];
	$phone = $_POST['phone'];
	$password = $_POST['password'];

	if($db->isUserExisted($email)){
		$response["error"] = TRUE;
		$response["error_msg"] = "A user already exists with ".$email;
		echo json_encode($response);

	}

	else{
		//create new user
		$user = $db->storeUser($username,$gender,$email,$phone,$password);
		if($user){
			$response["error"] = FALSE;
			$response["uid"] = $user["unique_id"];
			$response["user"]["username"] = $user["username"];
			$response["user"]["email"] = $user["email"];
			$response["user"]["phone"] = $user["phone"];
			$response["user"]["gender"] = $user["gender"];
			$response["user"]["created_at"] = $user["created_at"];
			$response["user"]["updated_at"] = $user["updated_at"];
			echo json_encode($response);
		}
		else{
			$response["error"] = TRUE;
			$response["error_msg"] = "An unkown error occured in registration";
			echo json_encode($response);
		}
	}
}

else{
	$response["error"] = TRUE;
	$response["error_msg"] = "Required parameters username, gender, email, password are missing";
	echo json_encode($response);
}

?>