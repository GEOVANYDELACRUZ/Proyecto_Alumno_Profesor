<?php
$hostname_localhost="localhost";
$database_localhost="id21315300_project";
$username_localhost="id21315300_gdelac";
$password_localhost="769gDTC*";

$json['img']=array();

	//if(true){)
	if(isset($_GET["codC"])&&isset($_GET["coment"])){
		
		$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
		if ($conexion->connect_error) {
			die("Connection failed: " . $conexion->connect_error);
		  }
		
		$codC=$_GET['codC'];
		$coment=$_GET['coment'];
		echo "Connected successfully";
		
		$consulta="call actualizar_comentario ('$codC','$coment');";
		if (mysqli_query($conexion,$consulta)) {
			echo "update record - NEW COMENTARIO";
			echo '<br>';
		} else {
			echo "Error: " . $sql . "<br>" . mysqli_error($conexion);
		}
		mysqli_close($conexion);
	}else{		
		echo '<br>';
		echo 'No ingreso ningun registro';
		echo '<br>';
	}
?>