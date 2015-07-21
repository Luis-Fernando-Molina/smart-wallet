// @author Bhavya Mehta
package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;

// Gives index bar view touched Y axis value, position of section and preview text value to list view 
public interface IIndexBarFilter {
	void filterList(float sideIndexY, int position, String previewText);
}
