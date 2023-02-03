<?php
    $name = isset($_POST['name']) ? $_POST['name'] : '';
    $type = isset($_POST['type']) ? $_POST['type'] : '';
    $size = isset($_POST['size']) ? $_POST['size'] : '';
    $extras = isset($_POST['extras']) ? $_POST['extras'] : '';
    if ($extras) {
        $extras_count = count($extras);
        if ($extras_count > 1) {
            $last_extra = array_pop($extras);
            $extras = implode(", ", $extras) . " and " . $last_extra;
        } else {
            $extras = implode(", ", $extras);
        }
    }

?>


<body style="background-color: black; color: white;">
<div style="border: 1px solid white; padding: 10px;">
<p>Name: <?php echo $name ?> </p>
<p>Type: <?php echo $type ?> </p>
<p>Size: <?php echo $size ?> </p>
<p>Extras: <?php echo $extras ?> </p>
</div>
</body>