<?php
$hostname_localhost="localhost";
$database_localhost="id21315300_project";
$username_localhost="id21315300_gdelac";
$password_localhost="769gDTC*";

$json=array();

    if(isset($_GET["dni"])&isset($_GET["pass"])){
        $conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
		if ($conexion->connect_error) {
		  die("Connection failed: " . $conexion->connect_error);
		}

        $dni=$_GET['dni'];
        $pass=$_GET['pass'];
        $respuesta="call ingresar_alumno('$dni',$pass);";
		$resultado=mysqli_query($conexion,$respuesta);
		
        if($registro=mysqli_fetch_array($resultado)){
            
                $result["DNI"]=$registro['DNI'];
			    $result["alumNombres"]=$registro['alumNombres'];
			    $result["alumAPaterno"]=$registro['alumAPaterno'];
			    $result["alumAPMaterno"]=$registro['alumAPMaterno'];
			    $json['tblAlumno'][]=$result;
                
           
        }
        else{
			  echo "Error: " . $sql . "<br>" . mysqli_error($conexion);
			$result["DNI"]=0;
			$result["alumNombres"]='No registra';
			$result["alumAPaterno"]='No registra';
			$result["alumAPMaterno"]='No registra';
			$json['tblAlumno'][]=$result;
        }
		mysqli_close($conexion);
		echo json_encode($json);
    }
    else{
            $resultar["DNI"]=0;
            $resultar["alumNombres"]='WS no retorna';
            $resultar["alumAPaterno"]='WS no retorna';
            $resultar["alumAPMaterno"]='WS no retorna';
            $json['tblAlumno'][]=$resultar;
            echo json_encode($json);
      }
?>