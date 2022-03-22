import os

from flask import Flask , request , redirect ,flash , url_for
from werkzeug.utils import secure_filename
from pdf2docx import Converter
from docx2pdf import convert
from PIL import Image


def convert_pdf_to_docx(input_name, output_name):
  cv = Converter(input_name)
  cv.convert(output_name)
  cv.close()

def convert_docx_to_pdf(input_name,output_name):
    docx_file = input_name
    convert(docx_file)

def convert_jpg_to_pdf(input_name,output_name):
    image_1 = Image.open(input_name)
    im_1 = image_1.convert('RGB')
    im_1.save(UPLOAD_FOLDER)

def convert_png_to_jpg(input_name,output_name):
            
   
  im = Image.open(input_name)
  rgb_im = im.convert('RGB')
  rgb_im.save(output_name)
  
def convert_file(input_name):
  filename = input_name.split('.')[0]
  old_extension = input_name.split('.')[-1]

  if old_extension == 'pdf':
    extension = 'docx'
    convert_pdf_to_docx(
      os.path.join(app.config['UPLOAD_FOLDER'], input_name), 
      os.path.join(app.config['UPLOAD_FOLDER'], filename + '-edited.' + extension)
      )
  elif old_extension == 'docx':
      extension = 'pdf'
      convert_docx_to_pdf(
      os.path.join(app.config['UPLOAD_FOLDER'], input_name), 
      os.path.join(app.config['UPLOAD_FOLDER'], filename + '-edited.' + extension)
      )
  elif old_extension == 'jpg':
      extension = 'pdf'
      convert_jpg_to_pdf(
      os.path.join(app.config['UPLOAD_FOLDER'], input_name), 
      os.path.join(app.config['UPLOAD_FOLDER'], filename + '-edited.' + extension)
      )
  elif old_extension == 'png':
      extension = 'jpg'
      convert_png_to_jpg(
      os.path.join(app.config['UPLOAD_FOLDER'], input_name), 
      os.path.join(app.config['UPLOAD_FOLDER'], filename + '-edited.' + extension)
      )
  else:
    print('Invalid!!')


app=Flask(__name__)


UPLOAD_FOLDER = "C:/Upload" 
ALLOWED_EXTENSIONS = {'txt', 'pdf','docx','png','jpg','jpeg'} 

app.config["UPLOAD_FOLDER"] = UPLOAD_FOLDER

def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS


@app.route('/', methods=['GET', 'POST'])
def upload_file():
    if request.method == 'POST':

        if 'file' not in request.files:
            flash('No file part')
            return redirect(request.url)
        file = request.files['file']

        if file.filename == '':
            flash('No selected file')
            return redirect(request.url)
        if file and allowed_file(file.filename):
            filename = secure_filename(file.filename)
            file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))

            # add code for conversion here
            convert_file(filename)
            
            return redirect(url_for('upload_file', name=filename))
    return '''
    <!doctype html>
    <title>Upload new File</title>
    <h1>Upload new File</h1>
    <form method=post enctype=multipart/form-data>
      <input type=file name=file>
      <input type=submit value=Upload>
    </form>
    '''

app.run(debug=True, host="localhost")