<?php
$hostname_localhost="localhost";
$database_localhost="id21315300_project";
$username_localhost="id21315300_gdelac";
$password_localhost="769gDTC*";

$json=array();

	if(isset($_GET["cod"]) && isset($_GET["comentario"])){
		
		$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
		if ($conexion->connect_error) {
		  die("Connection failed: " . $conexion->connect_error);
		}
		$cod=$_GET['cod'];
		$comentario=$_GET['comentario'];
		
		$consulta="UPDATE tblCurs_Nota set cursNComment = '$comentario' where IDCursNota = $cod;";
		if (mysqli_query($conexion,$consulta)) {
            $msj = 1;  
        } else {
            $msj = 0;
        }
		mysqli_close($conexion);
        $json['msj'] = $msj;
        echo json_encode($json);
	}else{
		
		echo '<br>';
		echo 'No ingreso ningun registro';
		echo '<br>';
	}
?>