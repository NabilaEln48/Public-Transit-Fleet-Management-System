<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Log GPS Event - PTFMS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .form-section {
            display: none;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="index.jsp">
                <i class="fas fa-bus"></i> PTFMS
            </a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="GPSTrackingServlet?action=dashboard">GPS Tracking</a>
                <a class="nav-link" href="operator.jsp">Back</a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="card">
                    <div class="card-header">
                        <h4 class="card-title mb-0">
                            <i class="fas fa-map-marker-alt"></i> Log GPS Event
                        </h4>
                        <small class="text-muted">Record vehicle location, station events, or break activities</small>
                    </div>
                    <div class="card-body">
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <i class="fas fa-exclamation-circle"></i> ${errorMessage}
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                        </c:if>

                        <c:if test="${not empty successMessage}">
                            <div class="alert alert-success alert-dismissible fade show" role="alert">
                                <i class="fas fa-check-circle"></i> ${successMessage}
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                        </c:if>

                        <div class="row mb-4">
                            <div class="col-12">
                                <label class="form-label fw-bold">Select Event Type:</label>
                                <div class="btn-group w-100" role="group">
                                    <input type="radio" class="btn-check" name="eventTypeSelector" id="locationEvent" value="location">
                                    <label class="btn btn-outline-primary" for="locationEvent">
                                        <i class="fas fa-map-marker-alt"></i> Location Update
                                    </label>
                                    <input type="radio" class="btn-check" name="eventTypeSelector" id="stationEvent" value="station">
                                    <label class="btn btn-outline-success" for="stationEvent">
                                        <i class="fas fa-train"></i> Station Event
                                    </label>
                                    <input type="radio" class="btn-check" name="eventTypeSelector" id="breakEvent" value="break">
                                    <label class="btn btn-outline-warning" for="breakEvent">
                                        <i class="fas fa-coffee"></i> Break Event
                                    </label>
                                </div>
                            </div>
                        </div>

                        <div id="locationForm" class="form-section">
                            <h2>Log Location Update</h2>
                            <form action="GPSTrackingServlet" method="post">
                                <input type="hidden" name="action" value="logLocation">
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="locationVehicleId" class="form-label">
                                            <i class="fas fa-truck"></i> Vehicle ID *
                                        </label>
                                        <input type="text" class="form-control" id="locationVehicleId" name="vehicleId" required placeholder="Enter vehicle ID (e.g., BUS001, TRAIN123)">
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="locationRouteId" class="form-label">
                                            <i class="fas fa-route"></i> Current Route
                                        </label>
                                        <input type="number" class="form-control" id="locationRouteId" name="routeId" min="1" placeholder="Enter route ID (optional)">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="gpsLat" class="form-label">
                                            <i class="fas fa-compass"></i> Latitude *
                                        </label>
                                        <input type="number" step="any" class="form-control" id="gpsLat" name="gpsLat" required placeholder="Enter latitude (e.g., 45.4215)">
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="gpsLng" class="form-label">
                                            <i class="fas fa-compass"></i> Longitude *
                                        </label>
                                        <input type="number" step="any" class="form-control" id="gpsLng" name="gpsLng" required placeholder="Enter longitude (e.g., -75.6972)">
                                    </div>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label for="locationLinkedStation" class="form-label">
                                        <i class="fas fa-map-pin"></i> Linked Station ID (Optional)
                                    </label>
                                    <input type="text" class="form-control" id="locationLinkedStation" name="linkedStation" placeholder="Enter linked station ID (e.g., Station1)">
                                </div>
                                <div class="mb-3">
                                    <label for="locationNotes" class="form-label">
                                        <i class="fas fa-sticky-note"></i> Notes
                                    </label>
                                    <textarea class="form-control" id="locationNotes" name="notes" rows="3" placeholder="Additional notes about the location update..."></textarea>
                                </div>
                                <div class="d-flex justify-content-between">
                                    <a href="GPSTrackingServlet?action=dashboard" class="btn btn-secondary">
                                        <i class="fas fa-arrow-left"></i> Back to Dashboard
                                    </a>
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fas fa-map-marker-alt"></i> Log Location Update
                                    </button>
                                </div>
                            </form>
                        </div>

                        <div id="stationForm" class="form-section">
                            <h2>Log Station Event</h2>
                            <form action="GPSTrackingServlet" method="post">
                                <input type="hidden" name="action" value="logStation">
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="stationVehicleId" class="form-label">
                                            <i class="fas fa-truck"></i> Vehicle ID *
                                        </label>
                                        <input type="text" class="form-control" id="stationVehicleId" name="vehicleId" required placeholder="Enter vehicle ID (e.g., BUS001, TRAIN123)">
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="stationId" class="form-label">
                                            <i class="fas fa-train"></i> Station *
                                        </label>
                                        <select class="form-control" id="stationId" name="stationId" required>
                                            <option value="" disabled selected>Select a station</option>
                                            <option value="Station1">Downtown Station</option>
                                            <option value="Station2">Central Station</option>
                                            <option value="Station3">North Station</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="stationEventType" class="form-label">
                                            <i class="fas fa-exchange-alt"></i> Event Type *
                                        </label>
                                        <select class="form-select" id="stationEventType" name="eventType" required>
                                            <option value="">Select event type</option>
                                            <option value="Station Arrival">Station Arrival</option>
                                            <option value="Station Departure">Station Departure</option>
                                        </select>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="expectedArrivalTime" class="form-label">
                                            <i class="fas fa-clock"></i> Expected Arrival Time
                                        </label>
                                        <input type="datetime-local" class="form-control" id="expectedArrivalTime" name="expectedArrivalTime">
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label for="stationNotes" class="form-label">
                                        <i class="fas fa-sticky-note"></i> Notes
                                    </label>
                                    <textarea class="form-control" id="stationNotes" name="notes" rows="3" placeholder="Additional notes about the station event..."></textarea>
                                </div>
                                <div class="d-flex justify-content-between">
                                    <a href="GPSTrackingServlet?action=dashboard" class="btn btn-secondary">
                                        <i class="fas fa-arrow-left"></i> Back to Dashboard
                                    </a>
                                    <button type="submit" class="btn btn-success">
                                        <i class="fas fa-train"></i> Log Station Event
                                    </button>
                                </div>
                            </form>
                        </div>

                        <div id="breakForm" class="form-section">
                            <h2>Log Break Event</h2>
                            <form action="GPSTrackingServlet" method="post" id="breakFormTag">
                                <input type="hidden" name="action" value="logBreak">
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="breakVehicleId" class="form-label">
                                            <i class="fas fa-truck"></i> Vehicle ID *
                                        </label>
                                        <input type="text" class="form-control" id="breakVehicleId" name="vehicleId" required placeholder="Enter vehicle ID (e.g., BUS001, TRAIN123)">
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="breakEventType" class="form-label">
                                            <i class="fas fa-play-pause"></i> Break Action *
                                        </label>
                                        <select class="form-select" id="breakEventType" name="eventType" required>
                                            <option value="">Select break action</option>
                                            <option value="Break Start">Start Break</option>
                                            <option value="Break End">End Break</option>
                                            <option value="Out of Service">Out of Service</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label for="breakNotes" class="form-label">
                                        <i class="fas fa-sticky-note"></i> Reason/Notes *
                                    </label>
                                    <textarea class="form-control" id="breakNotes" name="notes" rows="3" required placeholder="Please specify the reason for the break (e.g., lunch break, maintenance, driver change, etc.)"></textarea>
                                    <div class="form-text">Please provide details about the break or out-of-service reason.</div>
                                </div>
                                <div class="d-flex justify-content-between">
                                    <a href="GPSTrackingServlet?action=dashboard" class="btn btn-secondary">
                                        <i class="fas fa-arrow-left"></i> Back to Dashboard
                                    </a>
                                    <button type="submit" class="btn btn-warning">
                                        <i class="fas fa-coffee"></i> Log Break Event
                                    </button>
                                </div>
                            </form>
                        </div>

                        <div id="instructions" class="text-center py-5">
                            <i class="fas fa-info-circle fa-3x text-muted mb-3"></i>
                            <h5 class="text-muted">Select an Event Type</h5>
                            <p class="text-muted">Choose the type of GPS event you want to log from the options above.</p>
                        </div>
                    </div>
                </div>
                <div class="card mt-4">
                    <div class="card-header">
                        <h6 class="card-title mb-0">
                            <i class="fas fa-question-circle"></i> Help & Guidelines
                        </h6>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-4">
                                <h6 class="text-primary"><i class="fas fa-map-marker-alt"></i> Location Updates</h6>
                                <ul class="small">
                                    <li>Log regular position updates</li>
                                    <li>Include current route if applicable</li>
                                    <li>Used for real-time tracking</li>
                                    <li>Vehicle ID examples: BUS001, TRAIN123</li>
                                </ul>
                            </div>
                            <div class="col-md-4">
                                <h6 class="text-success"><i class="fas fa-train"></i> Station Events</h6>
                                <ul class="small">
                                    <li>Record arrivals and departures</li>
                                    <li>Include expected vs actual times</li>
                                    <li>Important for schedule analysis</li>
                                    <li>Vehicle ID examples: BUS001, TRAIN123</li>
                                </ul>
                            </div>
                            <div class="col-md-4">
                                <h6 class="text-warning"><i class="fas fa-coffee"></i> Break Events</h6>
                                <ul class="small">
                                    <li>Log when taking breaks</li>
                                    <li>Always specify the reason</li>
                                    <li>Include start and end times</li>
                                    <li>Vehicle ID examples: BUS001, TRAIN123</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Get the logType from the URL parameter, defaulting to 'location' if not present
            const urlParams = new URLSearchParams(window.location.search);
            const initialLogType = urlParams.get('logType') || 'location';
            
            // --- DEBUGGING STATEMENT for initial log type ---
            console.log('DEBUG: Initial logType from URL is: ' + initialLogType);

            // Mapping of radio button values to form IDs
            const forms = {
                'location': document.getElementById('locationForm'),
                'station': document.getElementById('stationForm'),
                'break': document.getElementById('breakForm')
            };
            const instructions = document.getElementById('instructions');

            function showForm(type) {
                Object.values(forms).forEach(form => {
                    if (form) form.style.display = 'none';
                });
                if (instructions) instructions.style.display = 'none';
                
                const formToShow = forms[type];
                if (formToShow) {
                    formToShow.style.display = 'block';
                }
            }
            
            // Function to handle URL-based form display
            function handleInitialFormDisplay() {
                const radioInput = document.getElementById(initialLogType + 'Event');
                if (radioInput) {
                    radioInput.checked = true;
                    showForm(initialLogType);
                }
            }
            
            handleInitialFormDisplay();

            // Add event listeners for radio button changes
            const eventTypeRadios = document.querySelectorAll('input[name="eventTypeSelector"]');
            eventTypeRadios.forEach(radio => {
                radio.addEventListener('change', function() {
                    if (this.checked) {
                        showForm(this.value);
                    }
                });
            });

            // Set default datetime to current time
            const expectedArrivalInput = document.getElementById('expectedArrivalTime');
            if (expectedArrivalInput) {
                const now = new Date();
                const formattedDateTime = now.getFullYear() + '-' +
                    String(now.getMonth() + 1).padStart(2, '0') + '-' +
                    String(now.getDate()).padStart(2, '0') + 'T' +
                    String(now.getHours()).padStart(2, '0') + ':' +
                    String(now.getMinutes()).padStart(2, '0');
                expectedArrivalInput.value = formattedDateTime;
            }

            // Enhanced form validation and submission debugging
            document.querySelectorAll('form').forEach(form => {
                form.addEventListener('submit', function(e) {
                    // Your existing vehicle ID validation
                    const vehicleIdInput = form.querySelector('input[name="vehicleId"]');
                    if (vehicleIdInput && vehicleIdInput.value) {
                        const vehicleId = vehicleIdInput.value.trim();
                        const validPattern = /^[A-Za-z0-9-_]+$/;
                        
                        if (!validPattern.test(vehicleId)) {
                            e.preventDefault();
                            alert('Invalid vehicle ID format. Use only letters, numbers, hyphens, and underscores.');
                            vehicleIdInput.focus();
                            return;
                        }
                        
                        if (vehicleId.length === 0 || vehicleId.length > 20) {
                            e.preventDefault();
                            alert('Vehicle ID must be between 1 and 20 characters.');
                            vehicleIdInput.focus();
                            return;
                        }
                    }

                    if (!form.checkValidity()) {
                        e.preventDefault();
                        e.stopPropagation();
                    }

                    // --- DEBUGGING STATEMENTS for all form submissions ---
                    const formAction = form.querySelector('input[name="action"]');
                    if (formAction) {
                        console.log('*** Debugging Form Submission for:', formAction.value, '***');
                        const formData = new FormData(form);
                        for (let [key, value] of formData.entries()) {
                            console.log(key + ':', value);
                        }
                        console.log('**********************************************');
                    }
                    
                    form.classList.add('was-validated');
                });
            });

            // Auto-hide alerts after 5 seconds
            setTimeout(function() {
                const alerts = document.querySelectorAll('.alert');
                alerts.forEach(function(alert) {
                    if (alert.classList.contains('show')) {
                        const bsAlert = new bootstrap.Alert(alert);
                        bsAlert.close();
                    }
                });
            }, 5000);
        });
    </script>
</body>
</html>