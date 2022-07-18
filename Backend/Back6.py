from fileinput import filename
import os
import docx2txt

from flask import Flask, request, redirect, flash, url_for, render_template, current_app
from werkzeug.utils import secure_filename
from pdf2docx import Converter
from docx2pdf import convert
from PIL import Image
from flask import send_file, send_from_directory, safe_join, abort
from bs4 import BeautifulSoup
from fpdf import FPDF


def convert_pdf_to_docx(input_name, output_name):
    cv = Converter(input_name)
    cv.convert(output_name)
    cv.close()


def convert_docx_to_pdf(input_name, output_name):
    docx_file = input_name
    convert(docx_file)


def convert_png_to_pdf(input_name, output_name):
    image_1 = Image.open(input_name)
    im_1 = image_1.convert('RGB')
    UPLOAD_FOLDER = "C:/Upload"
    im_1.save(UPLOAD_FOLDER)


def convert_docx_to_txt(input_name, output_name):
    text = docx2txt.process(input_name)
    with open(output_name, "w") as text_file:
        print(text, file=text_file)


def convert_jpg_to_png(input_name, output_name):

    im1 = Image.open(input_name)
    im1.save(input_name)


def convert_txt_to_pdf(input_name, output_name):
    pdf = FPDF()
    pdf.add_page()
    pdf.set_font("Arial", size=15)
    f = open(input_name, "r")
    for x in f:
        pdf.cell(200, 10, txt=x, ln=1, align='C')

        pdf.output(output_name)


def convert_xlsx_to_pdf(input_name, output_name):
    from win32com import client

    input_file = input_name

    output_file = output_name

    app = client.DispatchEx("Excel.Application")
    app.Interactive = False
    app.Visible = False
    Workbook = app.Workbooks.Open(input_file)
    try:
        Workbook.ActiveSheet.ExportAsFixedFormat(0, output_file)
    except Exception as e:
        print("Failed to convert in PDF format.Please confirm environment meets all the requirements  and try again")
        print(str(e))
    finally:
        Workbook.Close()
    app.Exit()


def convert_file(input_name):
    filename = input_name.split('.')[0]
    old_extension = input_name.split('.')[-1]
   # extension = input ()

    if old_extension == 'pdf':
        extension = 'docx'
        convert_pdf_to_docx(
            os.path.join(app.config['UPLOAD_FOLDER'], input_name),
            os.path.join(app.config['UPLOAD_FOLDER'],
                         filename + '-edited.' + extension)
        )
    elif old_extension == 'docx':
        extension = 'pdf'
        convert_docx_to_pdf(
            os.path.join(app.config['UPLOAD_FOLDER'], input_name),
            os.path.join(app.config['UPLOAD_FOLDER'],
                         filename + '-edited.' + extension)
        )
    elif old_extension == 'png':
        extension = 'pdf'
        convert_png_to_pdf(
            os.path.join(app.config['UPLOAD_FOLDER'], input_name),
            os.path.join(app.config['UPLOAD_FOLDER'],
                         filename + '-edited.' + extension)
        )
    elif old_extension == 'jpg':
        extension = 'png'
        convert_jpg_to_png(
            os.path.join(app.config['UPLOAD_FOLDER'], input_name),
            os.path.join(app.config['UPLOAD_FOLDER'],
                         filename + '-edited.' + extension)
        )
    elif old_extension == 'txt':
        extension = 'pdf'
        convert_txt_to_pdf(
            os.path.join(app.config['UPLOAD_FOLDER'], input_name),
            os.path.join(app.config['UPLOAD_FOLDER'],
                         filename + '-edited.' + extension)
        )
    elif old_extension == 'xlsx':
        extension = 'pdf'
        convert_xlsx_to_pdf(
            os.path.join(app.config['UPLOAD_FOLDER'], input_name),
            os.path.join(app.config['UPLOAD_FOLDER'],
                         filename + '-edited.' + extension)
        )

    else:
        print('Invalid!!')


# new_filename = filename + '-edited.' + extension

app = Flask(__name__)
app.secret_key = "super secret key"


UPLOAD_FOLDER = "C:/Upload"
ALLOWED_EXTENSIONS = {'txt', 'pdf', 'docx', 'png', 'jpg', 'jpeg'}

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

            convert_file(filename)
            # download(new_filename)

        return redirect(url_for('upload_file', name=filename))
    return render_template('index.html')


def download(filename):
    return send_from_directory(app.config["UPLOAD_FOLDER"], filename, as_attachment=True)


app.run(debug=True, host="localhost")
