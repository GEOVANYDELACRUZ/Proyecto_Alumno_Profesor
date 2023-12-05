<?php
$hostname_localhost="localhost";
$database_localhost="id21315300_project";
$username_localhost="id21315300_gdelac";
$password_localhost="769gDTC*";

$json=array();

    if(isset($_GET["curso"])&&isset($_GET["prof"])){
        $conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
        $curso=$_GET['curso'];
        $prof=$_GET['prof'];
        $consulta="SELECT * FROM tblAlumno A 
        INNER JOIN tblAlumCurs AC on A.DNI = AC.DNI
        INNER JOIN tblCurso C on C.IDCurso=AC.IDCurso
        INNER JOIN tblCursProf CP on CP.IDCurso=C.IDCurso
        where C.IDCurso = $curso  && CP.IDProfesor= $prof";
        $resultado=mysqli_query($conexion,$consulta);
		
        if($resp=mysqli_fetch_array($resultado)){
		    $resultado=mysqli_query($conexion,$consulta);
            while($registro=mysqli_fetch_array($resultado)){
                $result["DNI"]=$registro['DNI'];
			    $result["alumNombres"]=$registro['alumNombres'];
			    $result["alumAPaterno"]=$registro['alumAPaterno'];
			    $result["alumAPMaterno"]=$registro['alumAPMaterno'];
			    $json['tblCurso'][]=$result;                
           }
        }
        else{
			$result["DNI"]=-1;
			$result["alumNombres"]='No registra';
			$result["alumAPaterno"]='No registra';
			$result["alumAPMaterno"]='No registra';
			$json['tblCurso'][]=$result;
        }
		mysqli_close($conexion);
		echo json_encode($json);
    }
    else{
            $resultar["DNI"]=-1;
            $resultar["alumNombres"]='WS no retorna';
            $resultar["alumAPaterno"]='WS no retorna';
            $resultar["alumAPMaterno"]='WS no retorna';
            $json['tblCurso'][]=$resultar;
            echo json_encode($json);
      }
?>