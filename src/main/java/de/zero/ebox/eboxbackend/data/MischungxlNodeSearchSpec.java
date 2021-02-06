package de.zero.ebox.eboxbackend.data;

public class MischungxlNodeSearchSpec {

    //'/^filterid:([1-9][0-9]*)$/' -> kann auch nur filter laden
    private String titlesearch;//regexp wenn enclosed in /.../ sonst ilike mit %...%

    private String exclude_titlesearch;//regexp wenn enclosed in /.../ sonst ilike mit %...%

    public int[] include_tags;//tid der genres/tags die inkludiert (OR) werden können, kann invertiert werden mittels include_tags_exclude

    public boolean include_tags_exclude;//invertiert die logik von include_tags

    public int[] exclude_tags;//tid der genres/tags die exkludiert werden sollen

    public int[] artistnids;//einschränkung auf DJ-NIDs !!!ACHTUNG:

    public int[] uploaders;//einschränkung auf bestimmte Uploader UIDs

    public int mode;//eins der vordefinierten 13 Suchmuster (Filter)

    public boolean bookmarked;//nur vom aufrufer bookmarked sets

    public boolean lastheard;//nur bereits vom aufrufer gehörte Sets

    public boolean notheard;//nur noch nicht vom aufrufer gehörte Sets

    public boolean notvoted;//nur noch nicht vom aufrufer bewertete sets

    //KEIN FILTER
    public boolean score;//score soll mit selektiert werden

    public short[] myvotes;//nur bestimmte eigene Votes (0,1,2,3,4,5) zulassen

    public int entriesperartist;//Anzahl der Einträge pro Artist im Ergebnis reduzieren

    public int filterid;//vordefinierter filter...alle anderen werte sind dann OnTop (ergänzen/überschreiben den Filter)


}
