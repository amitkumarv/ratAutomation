package com.icreon.res_allocqa.utilities;

import java.io.IOException;
import java.util.ArrayList;
import org.openqa.selenium.WebElement;

public class JavaUtils {

	/*
	 * Method to check that a given sequence of string is sorted in ascending order
	 * @param strSequence ArrayList<WebElement> containing sequence of strings
	 * return boolean
	 */
	public boolean checkStringSortingAscending(ArrayList<WebElement> strSequence) throws IOException {		
		boolean sortedAscending = true;
		for (int i = 0; i < strSequence.size()-1; i++) {
			if(strSequence.get(i+1).getText().trim().compareToIgnoreCase(strSequence.get(i).getText().trim())<0) {
				sortedAscending = false;
				break;
			}
		}
		return sortedAscending;
	}
	
	/*
	 * Method to check that a given sequence of string is sorted in descending order
	 * @param strSequence ArrayList<WebElement> containing sequence of strings
	 * return boolean
	 */
	public boolean checkStringSortingDescending(ArrayList<WebElement> strSequence) throws IOException {
		boolean sortedDescending = true;
		for (int i = 0; i < strSequence.size()-1; i++) {
			if(strSequence.get(i).getText().trim().compareToIgnoreCase(strSequence.get(i+1).getText().trim())<0) {
				sortedDescending = false;
				break;
			}
		}
		return sortedDescending;
	}
	
	/*
	 * Method to check that a given sequence of integers is sorted in ascending order
	 * @param strSequence ArrayList<WebElement> containing sequence of integers
	 * return boolean
	 */
	public boolean checkIntSortingAscending(ArrayList<WebElement> intSequence) throws IOException {
		boolean sortedAscending = true;
		for (int i = 0; i < intSequence.size()-1; i++) {
			if(Integer.parseInt(intSequence.get(i).getText()) > Integer.parseInt(intSequence.get(i+1).getText())) {
				sortedAscending = false;
				break;
			}
		}
		return sortedAscending;
	}
	
	/*
	 * Method to check that a given sequence of integers is sorted in descending order
	 * @param strSequence ArrayList<WebElement> containing sequence of integers
	 * return boolean
	 */
	public boolean checkIntSortingDescending(ArrayList<WebElement> intSequence) throws IOException {
		boolean sortedDescending = true;
		for (int i = 0; i < intSequence.size()-1; i++) {
			if(Integer.parseInt(intSequence.get(i).getText()) < Integer.parseInt(intSequence.get(i+1).getText())) {
				sortedDescending = false;
				break;
			}
		}
		return sortedDescending;
	}
	
}
