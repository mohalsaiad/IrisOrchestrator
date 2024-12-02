public class IrisOrchestrator {

    enum State {
        IDLE,
        PROCESSING_REQUEST,
        IMAGE_RELEVANCY_ANALYSIS,
        CRAWLING,
        AGGREGATION,
        APPLICATION_ANALYSIS,
        FAILURE,
        SUCCESS
    }

    private State currentState;

    public IrisOrchestrator() {
        this.currentState = State.IDLE;
    }

    public void processRequest(RequestData request) {
        switch (currentState) {
            case IDLE:
                if (request.hasImage() && !request.hasApplicationDocument()) {
                    currentState = State.IMAGE_RELEVANCY_ANALYSIS;
                    performImageRelevancyAnalysis(request);
                } else if (!request.hasImage() && request.hasApplicationDocument()) {
                    currentState = State.CRAWLING;
                    performCrawling(request);
                } else {
                    currentState = State.FAILURE;
                }
                break;

            case IMAGE_RELEVANCY_ANALYSIS:
                if (performImageRelevancyAnalysis(request)) {
                    currentState = State.SUCCESS;
                } else {
                    currentState = State.FAILURE;
                }
                break;

            case CRAWLING:
                if (performCrawling(request)) {
                    currentState = State.AGGREGATION;
                    performAggregation(request);
                } else {
                    currentState = State.FAILURE;
                }
                break;

            case AGGREGATION:
                if (performAggregation(request)) {
                    currentState = State.APPLICATION_ANALYSIS;
                    performApplicationAnalysis(request);
                } else {
                    currentState = State.FAILURE;
                }
                break;

            case APPLICATION_ANALYSIS:
                if (performApplicationAnalysis(request)) {
                    currentState = State.SUCCESS;
                } else {
                    currentState = State.FAILURE;
                }
                break;

            case SUCCESS:
            case FAILURE:
                // Once in SUCCESS or FAILURE state, no further processing is done
                break;
        }
    }

    private boolean performImageRelevancyAnalysis(RequestData request) {
        System.out.println("Performing image relevancy analysis...");
        return true;
    }

    private boolean performCrawling(RequestData request) {
        System.out.println("Performing crawling...");
        return true;
    }

    private boolean performAggregation(RequestData request) {
        System.out.println("Performing aggregation...");
        return true;
    }

    private boolean performApplicationAnalysis(RequestData request) {
        System.out.println("Performing application analysis...");
        return true;
    }

    class RequestData {
        private boolean hasImage;
        private boolean hasApplicationDocument;

        public RequestData(boolean hasImage, boolean hasApplicationDocument) {
            this.hasImage = hasImage;
            this.hasApplicationDocument = hasApplicationDocument;
        }

        public boolean hasImage() {
            return hasImage;
        }

        public boolean hasApplicationDocument() {
            return hasApplicationDocument;
        }
    }

    public static void main(String[] args) {
        IrisOrchestrator orchestrator = new IrisOrchestrator();

        // Request with an image but no application document
        RequestData request1 = orchestrator.new RequestData(true, false);
        orchestrator.processRequest(request1);

        // Request with an application document but no image
        RequestData request2 = orchestrator.new RequestData(false, true);
        orchestrator.processRequest(request2);

        // Request with neither image nor application document (failure case)
        RequestData request3 = orchestrator.new RequestData(false, false);
        orchestrator.processRequest(request3);
    }
}
