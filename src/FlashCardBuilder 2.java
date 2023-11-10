private void loadFile(File selectedFile) {
    
    cardList = new ArrayList<FlashCard>();
    
    try {
        BufferedReader reader = new BufferedReader(new FileReader(selectedFile ));
        String line = null;

        while ((line = reader.readLine()) != null ) {
            makeCard(line);
        }

    } catch (Exception e) {
        System.out.println("Couldn't read the file card");
        e.printStackTrace();
    }
    cardIterator = cardList.iterator();
    showNextCard();
}

private void makeCard(String lineToParse) {
    String [] result = lineToParse.split("/")

    FlashCard card = new FlashCard(reuslt[0], result[1]);
    cardlist.add(card);
}

private void showNextCard() {
    currectCard = (FlashCard) cardIterato.next();

    display.setText(currectCard.getQuestion());
    showAnswer.setText("Show Answer");
    isShowAnswer = true;
}