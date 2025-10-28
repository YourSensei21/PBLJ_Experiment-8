import java.io.IOException;
import java.io.PrintWriter;

// Import the Servlet-specific classes.
// You will need the 'javax.servlet-api' library for this.
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is the Java Servlet that will handle the login request.
 * * The @WebServlet("/loginServlet") annotation is crucial.
 * It tells the server that any request to the URL "/loginServlet"
 * should be handled by this class. This MUST match the 'action'
_in the HTML form.
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    /**
     * We override the doPost method because our HTML form uses method="POST".
     * If the form used method="GET", we would override doGet.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Set the content type of the response we are sending back.
        // We are sending back an HTML page.
        response.setContentType("text/html");

        // 2. Get a PrintWriter to write the HTML response.
        PrintWriter out = response.getWriter();

        // 3. Retrieve the parameters from the request.
        // The strings "username" and "password" MUST match the 'name' attributes
        // in your HTML <input> tags.
        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        // 4. Validate the credentials (hardcoded, as per the objective)
        // In a real app, you would check this against a database.
        if ("admin".equals(user) && "123".equals(pass)) {
            
            // 5. If valid, show a personalized welcome message.
            // We are writing a full HTML page as the response.
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Welcome</title>");
            out.println("<script src=\"https://cdn.tailwindcss.com\"></script>"); // Add some style
            out.println("<link href=\"https://fonts.googleapis.com/css2?family=Inter:wght@400;600&display=swap\" rel=\"stylesheet\">");
            out.println("<style> body { font-family: 'Inter', sans-serif; } </style>");
            out.println("</head>");
            out.println("<body class='bg-gray-100 flex items-center justify-center min-h-screen'>");
            out.println("<div class='w-full max-w-md p-8 text-center bg-white rounded-xl shadow-lg'>");
            out.println("<h1 class='text-3xl font-bold text-green-600'>Welcome, " + user + "!</h1>");
            out.println("<p class='mt-4 text-gray-700'>You have successfully logged in.</p>");
            out.println("<a href='login.html' class='inline-block px-4 py-2 mt-6 font-semibold text-white bg-blue-600 rounded-lg shadow hover:bg-blue-700'>Logout</a>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

        } else {
            
            // 6. If invalid, show an error message.
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Error</title>");
            out.println("<script src=\"https://cdn.tailwindcss.com\"></script>");
            out.println("<link href=\"https://fonts.googleapis.com/css2?family=Inter:wght@400;600&display=swap\" rel=\"stylesheet\">");
            out.println("<style> body { font-family: 'Inter', sans-serif; } </style>");
            out.println("</head>");
            out.println("<body class='bg-gray-100 flex items-center justify-center min-h-screen'>");
            out.println("<div class='w-full max-w-md p-8 text-center bg-white rounded-xl shadow-lg'>");
            out.println("<h1 class='text-3xl font-bold text-red-600'>Login Failed!</h1>");
            out.println("<p class='mt-4 text-gray-700'>Invalid username or password.</p>");
            out.println("<a href='login.html' class='inline-block px-4 py-2 mt-6 font-semibold text-white bg-gray-500 rounded-lg shadow hover:bg-gray-600'>Try Again</a>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }

        // Close the writer
        out.close();
    }
}
