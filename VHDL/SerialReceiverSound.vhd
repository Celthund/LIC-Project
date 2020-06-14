----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    16:27:17 06/14/2020 
-- Design Name: 
-- Module Name:    SerialReceiverSound - Structural 
-- Project Name: 
-- Target Devices: 
-- Tool versions: 
-- Description: 
--
-- Dependencies: 
--
-- Revision: 
-- Revision 0.01 - File Created
-- Additional Comments: 
--
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity SerialReceiverSound is
    Port ( 
			  CLK : in STD_LOGIC;
			  RST : in STD_LOGIC;
	        SDX : in  STD_LOGIC;
           SCLK : in  STD_LOGIC;
           SS : in  STD_LOGIC;
           accept : in  STD_LOGIC;
           D : out  STD_LOGIC_VECTOR (3 downto 0);
           DXval : out  STD_LOGIC);
end SerialReceiverSound;

architecture Structural of SerialReceiverSound is
	COMPONENT SerialControl
		Port ( 
		     CLK : in  STD_LOGIC;
			  RST : in  STD_LOGIC;
			  enRx : in  STD_LOGIC;
           accept : in  STD_LOGIC;
           pFlag : in  STD_LOGIC;
           dFlag : in  STD_LOGIC;
           RXerror : in  STD_LOGIC;
			  wr : out STD_LOGIC;
           init : out  STD_LOGIC;
           DXval : out  STD_LOGIC);
	END COMPONENT;
	COMPONENT ParityCheck
		Generic (
			WIDTH : POSITIVE := 1
		);
		Port ( CLK : in  STD_LOGIC;
           RST : in  STD_LOGIC;
           Data : in  STD_LOGIC_VECTOR (WIDTH - 1 downto 0);
           Err : out  STD_LOGIC_VECTOR (WIDTH - 1 downto 0));
	END COMPONENT;
	COMPONENT Counter3bit
			Port ( CE : in  STD_LOGIC;
          CLK : in  STD_LOGIC;
          RST : in  STD_LOGIC;
          Q : out STD_LOGIC_VECTOR (2 downto 0));
	END COMPONENT;
	COMPONENT ShiftRegister
		Generic (
			WIDTH : POSITIVE := 2
		);
    Port ( CLK : in  STD_LOGIC;
			  RST : in  STD_LOGIC;
           Data : in  STD_LOGIC;
           enableShift : in  STD_LOGIC;
           Q : out STD_LOGIC_VECTOR (WIDTH - 1 downto 0));
	END COMPONENT;
	SIGNAL init, err, wr, dFlag, pFlag : STD_LOGIC;
	SIGNAL counterOut : STD_LOGIC_VECTOR (2 downto 0);
begin
	SerialCtrl: SerialControl
	PORT MAP (
		CLK => CLK,
		RST => RST,
		enRx => SS,
		accept => accept,
		pFlag => pFlag,
		dFlag => dFlag,
		RXerror => err,
		wr => wr,
		init => init,
		DXval => DXval
	);
	ParityChk: ParityCheck
	PORT MAP (
		CLK => SCLK,
		RST => init,
		Data(0) => SDX,
		Err(0) => err
	);
	Counter3: Counter3bit
	PORT MAP (
		CLK => SCLK,
		CE  => '1',
		RST => init,
		Q => counterOut
	);
	ShiftReg: ShiftRegister
	GENERIC MAP(
		WIDTH => 4
	)
	PORT MAP (
		CLK => SCLK,
		Data  => SDX,
		RST => RST,
		enableShift => wr,
		Q => D
	);
	dFlag <= (not counterOut(0) and not counterOut(1) and counterOut(2));
	pFlag <= (counterOut(0) and not counterOut(1) and counterOut(2));
	--dFlag <= '1' if (counterOut = "101") else dFlag <= '0';
	--pFlag  <= '1' if (counterOut = "110") else pFlag  <= '0';
end Structural;

