async def setupThymios():
    await notebook.start()
    robots = await notebook.get_nodes()
    global robotsMap
    for i, robot in enumerate(robots):
        physical_id = str(robot).replace("Node ", "")
        robotsMap[physical_id] = robot