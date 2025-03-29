const express = require('express');
const env = process.env.NODE_ENV || 'development';
const path = require('path');
require('dotenv').config({
  path: path.resolve(__dirname, `config/${env}.env`)
});

const app = express();

const multer = require('multer');
const xmlparser = require('express-xml-bodyparser');

app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(multer().any());
app.use(xmlparser());

const router = require('./src/routes');
app.use('/', router);

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Server running on port ${PORT}`);
});