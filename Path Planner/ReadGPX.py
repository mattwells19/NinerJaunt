import gpxpy
import math
from tkinter.filedialog import askopenfilename

points_data = '/home/mattw/Desktop/Reading GPX Files/Points.txt'

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


def addPoints(gpxInput):
    f = open(points_data, 'a')
    for track in gpxInput.tracks:
        for segment in track.segments:
            for point in segment.points:
                f.writelines('{0},{1}\n'.format(point.latitude, point.longitude))
    f.close()


gpx_file = open(askopenfilename())
gpx = gpxpy.parse(gpx_file)

addPoints(gpx)
showPoints()

