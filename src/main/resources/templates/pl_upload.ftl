<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <script type="text/javascript" src="${ctx!''}/resource/plugins/jquery/1.12.4/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx!''}/resource/plugins/plupload/3.1.2/plupload.full.min.js"></script>
    <!--[if lte IE 9]>
    <script type="text/javascript" src="${ctx!''}/resource/plugins/html5shiv/r29/html5.min.js"></script>
    <script type="text/javascript" src="${ctx!''}/resource/plugins/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
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
        url: '${ctx!""}/file/upload/savePersistentFiles',
        flash_swf_url: '${ctx!""}/resource/plugins/plupload/3.1.2/Moxie.swf',
        silverlight_xap_url: '${ctx!""}/resource/plugins/plupload/3.1.2/Moxie.xap',

        filters: {
            max_file_size: '10mb',
            mime_types: [
                { title: 'Image files', extensions: 'jpg,jpeg,gif,png' }
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
                        var reader = new moxie.file.FileReader();
                        console.log(reader);
                        reader.onload = function(event) {
                            var imageHtml = '<img src="' + this.result + '">';
                            $('#images').append(imageHtml);
                        }
                        reader.readAsDataURL(file.getSource());
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
                if (fileInfos.status == 'n') {
                    alert(fileInfos.message);
                }
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