package com.myselfie.myselfie.model;

import java.util.ArrayList;

public class VideoList {

	public static ArrayList<VideoItem> getVideoList() {
		ArrayList<VideoItem> resultList = new ArrayList<VideoItem>();

		VideoItem itm;

		itm = new VideoItem(
				1,
				"brave.jpg",
				"Determined to make her own path in life, Princess Merida defies a custom that brings chaos to her kingdom. Granted one wish, Merida must rely on her bravery and her archery skills to undo a beastly curse.",
				true);
		resultList.add(itm);

		itm = new VideoItem(
				2,
				"ice_age.jpg",
				"Manny, Diego, and Sid embark upon another adventure after their continent is set adrift. Using an iceberg as a ship, they encounter sea creatures and battle pirates as they explore a new world.",
				false);
		resultList.add(itm);

		itm = new VideoItem(
				3,
				"incredibles.jpg",
				"A family of undercover superheroes, while trying to live the quiet suburban life, are forced into action to save the world.",
				true);
		resultList.add(itm);

		itm = new VideoItem(
				4,
				"nemo.jpg",
				"After his son is captured in the Great Barrier Reef and taken to Sydney, a timid clownfish sets out on a journey to bring him home.",
				false);
		resultList.add(itm);

		itm = new VideoItem(
				5,
				"up.jpg",
				"By tying thousands of balloons to his home, 78-year-old Carl sets out to fulfill his lifelong dream to see the wilds of South America. Russell, a wilderness explorer 70 years younger, inadvertently becomes a stowaway.",
				false);
		resultList.add(itm);

		itm = new VideoItem(
				6,
				"brave.jpg",
				"Determined to make her own path in life, Princess Merida defies a custom that brings chaos to her kingdom. Granted one wish, Merida must rely on her bravery and her archery skills to undo a beastly curse.",
				false);
		resultList.add(itm);

		itm = new VideoItem(
				7,
				"ice_age.jpg",
				"Manny, Diego, and Sid embark upon another adventure after their continent is set adrift. Using an iceberg as a ship, they encounter sea creatures and battle pirates as they explore a new world.",
				false);
		resultList.add(itm);

		itm = new VideoItem(
				8,
				"incredibles.jpg",
				"A family of undercover superheroes, while trying to live the quiet suburban life, are forced into action to save the world.",
				false);
		resultList.add(itm);

		itm = new VideoItem(
				9,
				"nemo.jpg",
				"After his son is captured in the Great Barrier Reef and taken to Sydney, a timid clownfish sets out on a journey to bring him home.",
				false);
		resultList.add(itm);

		itm = new VideoItem(
				10,
				"up.jpg",
				"By tying thousands of balloons to his home, 78-year-old Carl sets out to fulfill his lifelong dream to see the wilds of South America. Russell, a wilderness explorer 70 years younger, inadvertently becomes a stowaway.",
				false);
		resultList.add(itm);

		return resultList;
	};

}
