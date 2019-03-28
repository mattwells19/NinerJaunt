import math

class point:
  #Create point object with self, a parent point, x,y coordinates, and a heuristic value
	def __init__(self, parent, lat, lon, h):
		self.parent = parent
		self.cord = [x,y]
		self.g = 1
		self.h = h
		self.f = self.g + self.h
	#Return the parent point of the calling point
	def getParent(self):
		return self.parent
	#Return (x,y) coordinates of calling point
	def getCord(self):
		return self.cord


class GridIO: #FIXME #modify to read from database?
	#Read grid from text file
	def ReadGrid(self, filename):
	grid = []
	with open(filename) as f:
	  for l in f.readlines():
		grid.append([int(x) for x in l.split()])
	return grid

  #Write grid to text file
	def WriteGrid(self, grid, filename):
	f = open(filename, 'w')
	for i in range(len(grid)):
	  for j in range(len(grid[i])):
		f.write(str(  grid[i][j]  ))
		f.write(' ')
	  f.write('\n')
	f.close()

#Find all possible children of  current node
def getChildren(grid, location):
	child = []
	if ((location[0]-1) >= 0):
		child.append([location[0]-1, location[1]]) 
	if ((location[1]-1) >= 0):
		child.append([location[0], location[1]-1]) 
	if ((location[0]+1) < len(grid)):
		child.append([location[0]+1, location[1]]) 
	if ((location[1]+1) < len(grid[0])):
		child.append([location[0], location[1]+1]) 
	return child

#Calculate heuristic value given two sets of lat and lon points
'''
The math for this function was obtained from 'https://andrew.hedges.name/experiments/haversine/'
Calculations assume miles
@:param cord1 - a container for a 2 element list consisting of (lat, lon)
@:param cord2 - a container for a 2 element list consisting of (lat, lon)
@:returns distance
'''
def calcH(cord1, cord2):
	dlon = cord2[1] - cord1[1]
	dlat = cord2[0] - cord1[0]
	dlon = dlon * math.pi / 180
	dlat = dlat * math.pi / 180
	a = pow((math.sin(dlat/2)),2) + math.cos(lat1) * math.cos(lat2) * pow((math.sin(dlon/2)),2)
	c = 2 * math.atan2( math.sqrt(a), math.sqrt(1-a) )
	return 3961 * c  # 3961 is the approximate radius of Earth in miles
	
	
#Print Grid in an easily readable format
def PrintGrid(grid):
	for i in range(len(grid)):
	for j in range(len(grid[i])):
	  if(grid[i][j]!= 1 and grid[i][j] != 'S' and grid[i][j]!='P' and grid[i][j]!='G'):
		grid[i][j] = 0

	  print (grid[i][j], end =" ")
	print()
	print()


#Check to see if point is in list.
def inList(que, pt):
	for point in que:
		if (point.getCord() == pt):
			return True
	return False
	
#work our way up from final goal node
def genPath(node, start, grid, goal):
	path = []
	while (node.getCord() != start.getCord()):
		path.append(node.getCord())
		#set 'P' for path
		grid[(node.getCord())[0]][(node.getCord())[1]] = 'P'
		#get next node
		node = node.getParent()

	#starting point gets overwritten by Path so we make it an 'S'
	grid[(start.getCord())[0]][(start.getCord())[1]] = 'S'
	#same for goal
	grid[goal[0]][goal[1]] = 'G'

	#print final grid
	PrintGrid(grid)
	return path

def main():

	#get names of starting and ending buildings
	#get lats and lons of buildings
	goal = [goalPointX, goalPointY] #destination building
	start = point(parent=[-1,-1], x=startLat, y=startLon, h=calcH([startLat,startLon],goal)) #starting building
	
	#Generate a grid where extremeties are the starting and ending lat & lons and populate potential lat and lons in between
	'''
	 ---------------
	|			goal|
	|				|
	|				|
	|				|
	|				|
	|				|
	|				|
	|start			|
	 ---------------
	'''
	#Read this area into a 2D list called grid
	#PrintGrid(grid) #for debugging/grid checking
	
	#########################################################################################################################
	#														BEGIN A*														#
	#########################################################################################################################

	openlist = [start]

	closedlist = []

	node = start

	states = 0

	while (node.getCord() != goal):
	states += 1
	if (node.getCord() != goal):

	  closedlist.append(node)
	  
	  family = getChildren(grid, node.getCord())

	  if (len(family) != 0):

		for c in family:

		  if (not inList(openlist, c) and grid[c[0]][c[1]] != 1 and not inList(closedlist, c)):
		    kid = point(parent=node, x=c[0], y=c[1], h=calcH(c, goal))
		    openlist.append(kid)

	openlist.sort(key=lambda x: x.f)
	  
	#get next node
	node = openlist.pop(0)
	
	#########################################################################################################################
	#														END A*															#
	#########################################################################################################################

	#path is final result. path is a list of lat and lon values
	path = genPath(node, start, grid, goal)
	#print(path) #for debugging

#call main function
main()




