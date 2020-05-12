<?php 
class DB_Functions{
	private $conn;
	function __construct(){
		require_once 'db_connect.php';
		$db = new DB_CONNECT();
		$this->conn = $db->connect();
	}

	function __destruct(){

	}
	//store new user
	//return user details
	public function storeUser($username,$gender,$email,$phone,$password){
		$uuid = uniqid('',true);
		$hash = $this->hashSSHA($password);
		$encrypted_password = $hash["encrypted"];
		$salt = $hash["salt"];
		$stmt = $this->conn->prepare("INSERT INTO users(unique_id,username,gender,email,phone,encrypted_password,salt,created_at) VALUES (?,?,?,?,?,?,?,NOW())");
		$stmt->bind_param("sssssss",$uuid,$username,$gender,$email,$phone,$encrypted_password,$salt);
		$result = $stmt->execute();
		$stmt->close();

		//check if user is stored successfully
		if($result){
			$stmt=$this->conn->prepare("SELECT * FROM users WHERE email = ?");
			$stmt->bind_param("s",$email);
			$stmt->execute();
			$user = $stmt->get_result()->fetch_assoc();
			$stmt->close();
			return $user;
		}
		else{
			return false;
		}
	}

	public function updateUser($unique_id,$username,$email,$phone){
		$stmt = $this->conn->prepare("UPDATE users SET username = ?,email = ?,phone = ? WHERE unique_id = ?");
		$stmt->bind_param("ssss",$username,$email,$phone,$unique_id);
		$result = $stmt->execute();
		$stmt->close();

		//check if user is stored successfully
		if($result){
			$stmt=$this->conn->prepare("SELECT * FROM users WHERE unique_id = ?");
			$stmt->bind_param("s",$unique_id);
			$stmt->execute();
			$user = $stmt->get_result()->fetch_assoc();
			$stmt->close();
			return $user;
		}
		else{
			return false;
		}
	}


	public function insertRecipe($unique_id,$recipe_name,$recipe_description){
		$stmt = $this->conn->prepare("INSERT INTO recipes(unique_id,recipe_name,recipe_description) VALUES (?,?,?)");
		$stmt->bind_param("sss",$unique_id,$recipe_name,$recipe_description);
		$result = $stmt->execute();
		$stmt->close();

		//check if user is stored successfully
		if($result){
			$stmt=$this->conn->prepare("SELECT * FROM recipes WHERE unique_id = ?");
			$stmt->bind_param("s",$unique_id);
			$stmt->execute();
			$user = $stmt->get_result()->fetch_assoc();
			$stmt->close();
			return $user;
		}
		else{
			return false;
		}
	}

	//get user by email and password
	public function getUserByUsernameAndPassword($username,$password){
		$stmt = $this->conn->prepare("SELECT * FROM users WHERE username = ?");
		$stmt->bind_param("s",$username);
		if($stmt->execute()){
			$user = $stmt->get_result()->fetch_assoc();
			$stmt->close();

			//verifying user password
			$salt = $user['salt'];
			$encrypted_password = $user['encrypted_password'];
			$hash = $this->checkhashSSHA($salt,$password);

			//check for password equality 
			if($encrypted_password == $hash){
				return $user;
			}

		}

		else{
			return NULL;
		}
	}

	//check if user exists

	public function isUserExisted($email){
		$stmt = $this->conn->prepare("SELECT email FROM users WHERE email = ?");
		$stmt->bind_param("s",$email);
		$stmt->execute();
		$stmt->store_result();
		if($stmt->num_rows > 0){
			$stmt->close();
			return true;
		}

		else{
			$stmt->close();
			return false;
		}
	}

	//encrypting password

	public function hashSSHA($password){
		$salt = sha1(rand());
		$salt = substr($salt,0,10);
		$encrypted = base64_encode(sha1($password.$salt,true).$salt);
		$hash = array("salt"=>$salt,"encrypted"=>$encrypted);
		return $hash;
	}

	//decrypting password

	public function checkhashSSHA($salt,$password){
		$hash = base64_encode(sha1($password.$salt,true).$salt);
		return $hash;
	}
}
 ?>
