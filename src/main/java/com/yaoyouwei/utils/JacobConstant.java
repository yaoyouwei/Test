package com.yaoyouwei.utils;

public class JacobConstant {
	//WdGoToItem Enumeration (Word)----------------------------------------------------------------------------------------- 
	//Specifies the type of item to move the insertion point or selection just prior to.

	public static final int wdGoToBookmark = -1;// A bookmark.
	public static final int wdGoToComment = 6;// A comment.
	public static final int wdGoToEndnote = 5;// An endnote.
	public static final int wdGoToEquation = 10;// An equation.
	public static final int wdGoToField = 7;// A field.
	public static final int wdGoToFootnote = 4;// A footnote.
	public static final int wdGoToGrammaticalError = 14;// A grammatical error.
	public static final int wdGoToGraphic = 8;// A graphic.
	public static final int wdGoToHeading = 11;// A heading.
	public static final int wdGoToLine = 3;// A line.
	public static final int wdGoToObject = 9;// An object.
	public static final int wdGoToPage = 1;// A page.
	public static final int wdGoToPercent = 12;// A percent.
	public static final int wdGoToProofreadingError = 15;// A proofreading
	public static final int wdGoToSection = 0;// A section.
	public static final int wdGoToSpellingError = 13;// A spelling error.
	public static final int wdGoToTable = 2;// A table.
	
	//------------------------------------------------------------------------------------------------------------------------------------
	//WdGoToDirection Enumeration (Word)
	//Specifies the position to which a selection or the insertion point is moved in relation to an object or to itself.
	public static final int wdGoToAbsolute = 1; // An absolute position.
	public static final int wdGoToFirst = 1; // The first instance of the specified object.
	public static final int wdGoToLast = -1; // The last instance of the specified object.
	public static final int wdGoToNext = 2; // The next instance of the specified object.
	public static final int wdGoToPrevious = 3; // The previous instance of the specified object.
	public static final int wdGoToRelative = 2; // A position relative to the current position.
	
	//------------------------------------------------------------------------------------------------------------------------------------
	//WdInformation Enumeration (Word)
	//Specifies the type of information returned about a specified selection or range.
	public static final int wdNumberOfPagesInDocument = 4 ;//Returns the number of pages in the document associated with the selection or range.
	 
	//------------------------------------------------------------------------------------------------------------------------------------
	//WdBuiltInProperty Enumeration (Word) 
	//Specifies a built-in document property.
	public static final int wdPropertyPages  = 14 ;//Page count.
	
	//------------------------------------------------------------------------------------------------------------------------------------
	//WdUnits Enumeration (Word)
	//Specifies a unit of measure to use.
	public static final int wdCell = 12; // A cell.
	public static final int wdCharacter = 1; // A character.
	public static final int wdCharacterFormatting = 13; // Character formatting.
	public static final int wdColumn = 9; // A column.
	public static final int wdItem = 16; // The selected item.
	public static final int wdLine = 5; // A line.
	public static final int wdParagraph = 4; // A paragraph.
	public static final int wdParagraphFormatting = 14; // Paragraph formatting.
	public static final int wdRow = 10; // A row.
	public static final int wdScreen = 7; // The screen dimensions.
	public static final int wdSection = 8; // A section.
	public static final int wdSentence = 3; // A sentence.
	public static final int wdStory = 6; // A story.
	public static final int wdTable = 15; // A table.
	public static final int wdWindow = 11; // A window.
	public static final int wdWord = 2; // A word.
	
}
