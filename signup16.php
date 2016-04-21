<?php
define('DB_HOST', '127.0.0.1');
define('DB_USER', 'test');
define('DB_PASS', '');
define('DB_NAME', 'test_db');

$link = mysql_connect(DB_HOST, DB_USER, DB_PASS);
$db = mysql_select_db(DB_NAME);


//mysql_query('SET NAMES cp1251');
mysql_query('SET NAMES utf8');

	
 function search ($query) {   
    $addString="{"; 
	$query = trim($query); 
    $query = mysql_real_escape_string($query);
    $query = htmlspecialchars($query); 

$q = "SELECT `COL 1`,`COL 2`, `COL 3`, `COL 4`, `COL 5`, `COL 6`, `COL 7`, `COL 8`,`COL 9` from `forms` where  (FIND_IN_SET(`COL 1`,(SELECT `col3` FROM `uniontable` where `col1` = '$query')))";
               $result = mysql_query($q);

 $n = "SELECT `col1`, `col2` FROM `uniontable` where `col1` = '$query'";         
			   $result1 = mysql_query($n);

    
 if (mysql_affected_rows() > 0) 
					 { 
                     $row = mysql_fetch_assoc($result); 
                     $row1 = mysql_fetch_assoc($result1);  				            
			         $num = mysql_num_rows($result);
						 
						 $i=0;
						
                do 
				{		
	//convert to JSON
					$addString .='"n'.$i.'":{';
					$addString .='"COL1":"'.$row['COL 1'].'",';
					$addString .='"COL2":"'.$row['COL 2'].'",';
					$addString .='"COL3":"'.$row['COL 3'].'",';
					$addString .='"COL4":"'.$row['COL 4'].'",';
					$addString .='"COL5":"'.$row['COL 5'].'",';
					$addString .='"COL6":"'.$row['COL 6'].'",';
					$addString .='"COL7":"'.$row['COL 7'].'",';
					$addString .='"COL8":"'.$row['COL 8'].'",';
					//$addString .=$row['COL 8'].',';
					$addString .='"COL9":"'.str_replace("\"","'",$row['COL 9']).'"';
					
						if ($i<$num-1) {
  
							 $addString .='},';
						}
						else {
							//echo '}}';
							$addString .='}}';
							}
					$i=$i+1;
                } 
				while ($row = mysql_fetch_assoc($result));           
            } 
			else 
			{
                $text = '<p>По вашему запросу ничего не найдено.</p>';
            }
         return $addString;
         }

//get from app
	$fullname = $_GET['fullname'];  
    $search_result = search ($fullname); 

    echo $search_result; 


mysql_close($link);
?>