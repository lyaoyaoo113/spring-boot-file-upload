<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <!--[if lte IE 9]>
    <script type="text/javascript" src="${ctx!''}/resource/plugins/html5shiv/3.7.3/html5shiv-printshiv.min.js"></script>
    <script type="text/javascript" src="${ctx!''}/resource/plugins/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <!--[if IE 9]>
	<script type="text/javascript" src="${ctx!''}/resource/plugins/css3pie/2.1.0-beta/PIE_IE9.js"></script>
    <![endif]-->
    <!--[if lt IE 9]>
	<script type="text/javascript" src="${ctx!''}/resource/plugins/css3pie/2.1.0-beta/PIE_IE678.js"></script>
    <![endif]-->
    <script type="text/javascript" src="${ctx!''}/resource/plugins/jquery/1.12.4/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx!''}/resource/plugins/plupload/3.1.2/plupload.full.min.js"></script>
</head>

<body>
    <div id="filelist">Your browser doesn't have Flash, Silverlight or HTML5 support.</div>
    <div id="images"></div>
    <div id="container">
        <a id="pickfiles" href="javascript:;">[Select files]</a>
        <a id="upload" href="javascript:;">[Upload files]</a>
    </div>
    <script type="text/javascript">
    var uploader = new plupload.Uploader({
        runtimes: 'html5,flash,silverlight,html4',
        browse_button: 'pickfiles', // you can pass an id...
        container: document.getElementById('container'), // ... or DOM Element itself
        url: '${ctx!""}/file/upload/saveImportExcelFiles',
        flash_swf_url: '${ctx!""}/resource/plugins/plupload/3.1.2/Moxie.swf',
        silverlight_xap_url: '${ctx!""}/resource/plugins/plupload/3.1.2/Moxie.xap',

        filters: {
            max_file_size: '20mb',
            mime_types: [
                { title: 'Excel files', extensions: 'xls' }
            ]
        },

        init: {
            PostInit: function() {
                document.getElementById('filelist').innerHTML = '';

                document.getElementById('upload').onclick = function(evnet) {
                    uploader.start();
                }
            },

            FilesAdded: function(up, files) {
                plupload.each(files, function(file) {
                    document.getElementById('filelist').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ') <b></b></div>';

                    var fileType = file.type;
                    if (fileType.indexOf('/gif') > -1) {
                        var fileReader = new moxie.file.FileReader();
                        fileReader.onload = function(event) {
                            var imageHtml = '<img src="' + this.result + '">';
                            $('#images').append(imageHtml);
                        }
                        fileReader.readAsDataURL(file.getSource());
                    } else if (fileType.indexOf('/jpeg') > -1 || fileType.indexOf('/png') > -1) {
                        var imageReader = new moxie.image.Image();
                        imageReader.onload = function(event) {
                            var imageHtml = '<img src="' + imageReader.getAsDataURL() + '">';
                            $('#images').append(imageHtml);
                        }
                        imageReader.load(file.getSource());
                    }
                });

                //uploader.start();
            },

            UploadProgress: function(up, file) {
                document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
            },

            FileUploaded: function(up, file, responseObject) {
                var fileInfos = JSON.parse(responseObject.response);
                console.log(fileInfos);
            },

            Error: function(up, err) {
                console.log('Error #' + err.code + ': ' + err.message);
                console.log(up);
            }
        }
    });

    uploader.init();
    </script>
</body>

</html>