const jsonServer = require('json-server')
const server = jsonServer.create()

const cors = require('cors');

server.use(cors());

server.get('/token/check', (req, res) => {
	res.jsonp({
		'name' : 'fakeuser',
		'role' : req.header('Authorization').startsWith('Bearer s') ? 'ROLE_SERVICE' : 'ROLE_ADMIN'
	})	
})

server.post('/token', (req, res) => {
	res.jsonp({
		'token' : 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYWtldXNlciIsInJvbGUiOiJST0xFX0FETUlOIiwiYXBwbmFtZSI6IkJMT0dfQVBQIiwiY3JlYXRlZCI6MTU5NTg5MTM2NTExMCwiZXhwIjoxNjU5MDA1MjY5fQ.Nrj_qVI_uMPNNPsyA51_VuRBNZ5Ha8PytBMR9J1iXVk'
	})
})

server.listen(process.env.PORT, () => {
	console.log('Auth Server (mock) is running')
})
