@app.get('/thymio')
def getThymioParams():
    assert request.method == 'GET'

    # check that is only an params named json
    if len(request.args) != 1 or 'json' not in request.args:
        return "Error: only one parameter named json is allowed", 400
    
    encoded_json = request.args.get('json', default=None)
    if encoded_json:
        # decode the URL
        decoded_json = unquote(encoded_json)
        try:
            command_data = json.loads(decoded_json)
            physical_id_thymio = command_data.get("id")
            left_motor = command_data.get("l")
            right_motor = command_data.get("r")
            moveThymio(robotsMap.get(physical_id_thymio) , left_motor, right_motor)
            return jsonify({"status": "ok", "message": "Data received", "ID": physical_id_thymio, "L": left_motor, "R": right_motor})
        except json.JSONDecodeError:
            return jsonify({"status": "error", "message": "Invalid JSON format"}), 400

def moveThymio(robot, left, right):
    aw(robot.lock())
    v = {
        "motor.left.target": [int(left)],
        "motor.right.target": [int(right)],
    }
    aw(robot.set_variables(v))
    aw(robot.unlock())