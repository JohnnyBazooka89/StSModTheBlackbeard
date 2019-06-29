This page is the destination for metric uploads.

<?php
if ($_SERVER['REQUEST_METHOD'] === 'PUT') {
    $json = file_get_contents('php://input');

    $directory = 'runs/' . date("Y") . "/" . date("m") . "/" . date("d") . "/";

    if (!is_dir($directory)) {
        mkdir($directory, 0755, true);
    }

    $file_name = $directory . time();
    $file = fopen($file_name, "w");
    fwrite($file, $json);
    fclose($file);
    chmod($file_name, 0644);
}
?>
