from pykml import parser
import math
from tkinter.filedialog import askopenfilename
from bs4 import BeautifulSoup

points_data = open(askopenfilename()).name

'''
This function will act as the Heurisitc for our A* search.
The math for this function was obtained from 'https://andrew.hedges.name/experiments/haversine/'
Calculations assume miles
@:param lat1
@:param lon1
@:param lat2
@:param lon2
@:returns distance
'''
def calcCrowDist(lat1, lon1, lat2, lon2):
   dlon = lon2 - lon1
   dlat = lat2 - lat1
   a = pow((math.sin(dlat/2)),2) + math.cos(lat1) * math.cos(lat2) * pow((math.sin(dlon/2)),2)
   c = 2 * math.atan2( math.sqrt(a), math.sqrt(1-a) )
   return 3961 * c  # 3961 is the approximate radius of Earth in miles


def showPoints():
    with open(points_data) as f:
        for l in f.readlines():
            print([float(x) for x in l.split(',')])



def process_coordinate_string(str):
	"""
	Take the coordinate string from the KML file, and break it up into [Lat,Lon,Lat,Lon...] for a CSV row
	"""
	space_splits = str.split(" ")
	ret = []
	# There was a space in between <coordinates>" "-80.123...... hence the [1:]
	for split in space_splits[1:]:
		comma_split = split.split(',')
		try:
			ret.append(comma_split[1])    # lat
			ret.append(comma_split[0])    # lng
		except:
			continue
	return ret
    
def addPoints(kmxInput):
	p = open(points_data, 'a')
	with open(kmxInput, 'r') as f:
		s = BeautifulSoup(f, 'xml')
		for coords in s.find_all('coordinates'):
			for i,line in enumerate(process_coordinate_string(coords.string)):
				if (i % 2 == 0):
					p.write(str(line) + ",")
				else:
					p.write(str(line) + '\n')
	f.close()


kmx_file = open(askopenfilename()).name

addPoints(kmx_file)
showPoints()

