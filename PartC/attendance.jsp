<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Attendance</title>
    <!-- We'll use the same styling tools -->
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Inter', sans-serif; }
    </style>
</head>
<body class="bg-gray-100">

    <div class="flex items-center justify-center min-h-screen">
        
        <div class="w-full max-w-lg p-8 space-y-6 bg-white rounded-xl shadow-lg">
            <h1 class="text-3xl font-bold text-center text-gray-800">Student Attendance Portal</h1>
            <p class="text-center text-gray-600">Please mark the student's attendance.</p>

            <!-- 
              This form submits data to the "attendanceServlet"
              using the "POST" method.
            -->
            <form action="attendanceServlet" method="POST" class="space-y-6">
                
                <div>
                    <label for="studentId" class="block text-sm font-medium text-gray-700">Student ID</label>
                    <input 
                        type="number" 
                        id="studentId" 
                        name="studentId" 
                        required
                        class="w-full px-4 py-3 mt-1 text-gray-800 bg-gray-50 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                        placeholder="e.g., 7001"
                    >
                </div>

                <div>
                    <label for="date" class="block text-sm font-medium text-gray-700">Date</label>
                    <input 
                        type="date" 
                        id="date" 
                        name="date" 
                        required
                        class="w-full px-4 py-3 mt-1 text-gray-800 bg-gray-50 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                    >
                </div>

                <div>
                    <label for="status" class="block text-sm font-medium text-gray-700">Status</label>
                    <select 
                        id="status" 
                        name="status" 
                        required
                        class="w-full px-4 py-3 mt-1 text-gray-800 bg-gray-50 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                    >
                        <option value="Present">Present</option>
                        <option value="Absent">Absent</option>
                    </select>
                </div>

                <div>
                    <button 
                        type="submit" 
                        class="w-full px-4 py-3 font-semibold text-white bg-green-600 rounded-lg shadow-md hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-green-500 focus:ring-offset-2 transition duration-200 ease-in-out"
                    >
                        Submit Attendance
                    </button>
                </div>
            </form>
        </div>

    </div>
</body>
</html>
