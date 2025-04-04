const express = require('express');
const router = express.Router();

router.all('*', (req, res) => {
    const body = { ...req.body };
    if (req.files) {
        body.__file__ = req.files.map(file => ({
            fieldname: file.fieldname,
            originalname: file.originalname,
            mimetype: file.mimetype
        }));
    }
    res.json({
        echo: "express",
        method: req.method,
        url: `${req.protocol}://${req.get('host')}${req.originalUrl}`,
        headers: req.headers,
        query: req.query,
        body: body
    });
});

module.exports = router;