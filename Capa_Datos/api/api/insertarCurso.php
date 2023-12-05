<?php
$hostname_localhost="localhost";
$database_localhost="id21315300_project";
$username_localhost="id21315300_gdelac";
$password_localhost="769gDTC*";

$json['img']=array();

	//if(true){)
	if(isset($_GET["curso"])&&isset($_GET["prof"])){
		
		$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
		if ($conexion->connect_error) {
		  die("Connection failed: " . $conexion->connect_error);
		}
		
		echo "Connected successfully";
		echo '<br>';
		$Curso=$_GET['curso'];
		echo "Curso = ";
		echo "$Curso";
		echo '<br>';
		$Profesor=$_GET['prof'];
		echo "Profesor = ";
		echo "$Profesor";
		echo '<br>';

		$consulta="SELECT curNombre from tblCurso where curNombre like '$Curso'";
        $resultado=mysqli_query($conexion,$consulta);
		if($resp=mysqli_fetch_array($resultado)){
			echo "Curso ya existente";
			echo '<br>';
		}else{
			$consulta="call insertar_curso ( '$Curso' )";
			if (mysqli_query($conexion,$consulta)) {
				echo "New record created successfully; CURSO NEW";
					echo '<br>';
					
			} else {
				echo "Error: " . $sql . "<br>" . mysqli_error($conexion);
			}
		}
		$consulta="SELECT curNombre FROM tblCursProf CP
		INNER JOIN tblCurso C ON C.IDCurso=CP.IDCurso
		WHERE CP.IDProfesor like '$Profesor' && C.curNombre like '$Curso'";
        $result=mysqli_query($conexion,$consulta);
		if($resp=mysqli_fetch_array($result)){
			echo "CURSO PROFESOR ya existente";
			echo '<br>';
		}else{
			$resultado="call insertar_prof_curso ('$Profesor','$Curso')";
			if (mysqli_query($conexion,$resultado)) {
					echo "New record created successfully; CURSO PROFESOR NEW";
			} else {
					echo "Error: " . $sql . "<br>" . mysqli_error($conexion);
			}
		}
		mysqli_close($conexion);
	}else{
		
		echo '<br>';
		echo 'No ingreso ningun registro';
		echo '<br>';
	}
?>