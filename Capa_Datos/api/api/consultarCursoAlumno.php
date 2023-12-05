<?php
$hostname_localhost="localhost";
$database_localhost="id21315300_project";
$username_localhost="id21315300_gdelac";
$password_localhost="769gDTC*";

$json=array();

    if(isset($_GET["codigo"])){
        $conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
        $codigo=$_GET['codigo'];
        $consulta="select * from tblCurso C
        INNER JOIN tblAlumCurs AC on C.IDCurso=AC.IDCurso
        INNER JOIN tblAlumno A on AC.DNI=A.DNI
        INNER JOIN tblCurs_Nota CN on AC.IDAlumC=CN.IDAlumC
        where AC.IDAlumC=$codigo";
        $resultado=mysqli_query($conexion,$consulta);
		
        if($resp=mysqli_fetch_array($resultado)){
		    $resultado=mysqli_query($conexion,$consulta);
            while($registro=mysqli_fetch_array($resultado)){
                $result["IDCursNota"]=$registro['IDCursNota'];
			    $result["curNombre"]=$registro['curNombre'];
			    $result["cursCriterio"]=$registro['cursCriterio'];
			    $result["cursNota"]=$registro['cursNota'];
			    $result["cursNComment"]=$registro['cursNComment'];
			    $result["cursNRespuesta"]=$registro['cursNRespuesta'];
			    $json['tblCurso'][]=$result;
                
           }
        }
        else{
			$result["IDCurso"]=-1;
			$result["curNombre"]='No registra';
			$result["IDNota"]='No registra';
			$result["notCriterio"]='No registra';
			$result["notValor"]='No registra';
			$result["notDescrip"]='No registra';
			$json['tblCurso'][]=$result;
        }
		mysqli_close($conexion);
		echo json_encode($json);
    }
    else{
            $resultar["IDCurso"]=-1;
            $resultar["curNombre"]='WS no retorna';
            $resultar["notCriterio"]='WS no retorna';
            $resultar["notValor"]='WS no retorna';
            $resultar["alumNombres"]='WS no retorna';
            $resultar["notDescrip"]='WS no retorna';
            $json['tblCurso'][]=$resultar;
            echo json_encode($json);
      }
?>